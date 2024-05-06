package br.com.nostramassa.gestao.services;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;

import br.com.nostramassa.gestao.dtos.pedido.PedidoDTO;
import br.com.nostramassa.gestao.dtos.pedido.PedidoItemDTO;
import br.com.nostramassa.gestao.models.sistema.Parametros;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Slf4j
@Service
public class ImpressoraService {
	
	@Autowired
	private ParametroService parametroService;
	
	public List<String> listaImpressoras(){
		List<String> nomesImpressoras = new ArrayList<>();
//		PrinterJob printerJob = PrinterJob.getPrinterJob();
        javax.print.PrintService[] printServices = PrinterJob.lookupPrintServices();
        if (printServices.length != 0) {
            for (javax.print.PrintService printService : printServices) {
            	nomesImpressoras.add(printService.getName());
            }
        }
		return nomesImpressoras;
	}
	
	public void print(String file) {
		try {
			PDDocument documento;
			try {
				PrintService servico = PrintServiceLookup.lookupDefaultPrintService();
				documento = PDDocument.load(new File(file));
				PrinterJob job = PrinterJob.getPrinterJob();
				  job.setPageable(new PDFPageable(documento));
				  
				  job.setPrintService(servico);
				  job.print();
				  documento.close();
			} catch (InvalidPasswordException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
			log.info("ok");
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		log.info("Imprimindo...");
	}
	
	private Map<String, Object> createParamsReport(PedidoDTO pedido) {
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("PEDIDO_NUMERO", pedido.getIdPedido().toString());
		params.put("PEDIDO_DATA_HORA", pedido.getDataPedido().toString());
		params.put("PEDIDO_CLIENTE", pedido.getClienteNome());
		params.put("PEDIDO_TELEFONE", pedido.getClienteTelefone());
		params.put("PEDIDO_ENDERECO", pedido.getEnderecoDescricao());
		params.put("PEDIDO_TAXA_ENTREGA", "0");
		params.put("PEDIDO_VALOR_RECEBER", pedido.getValor().toString());
		params.put("PEDIDO_OBSERVACAO", "");
		return params;
	}
	public static String timestampToStr(String pattern, Timestamp valor) {
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(Date.from(valor.toInstant()));
	}

	
	public String geraRelatorio(PedidoDTO pedidoDTO) {

		Parametros parametros = parametroService.getParametros();
		
		String nomeArquivo = String.format("%d_pedido.pdf",pedidoDTO.getIdPedido());
		File pastaPedidos = new File("pedidos");
		if (!pastaPedidos.exists()) {
		    pastaPedidos.mkdirs();
		}
		nomeArquivo = pastaPedidos.getPath() + File.separator + nomeArquivo;
		Map<String, Object> params = createParamsReport(pedidoDTO);
		String arquivoJarperString = "report/pedido_report.jrxml";
		
		if(pedidoDTO.getItensPedido() == null || pedidoDTO.getItensPedido().isEmpty()) {
			pedidoDTO.setItensPedido(Arrays.asList(new PedidoItemDTO()));
		}
		Resource resource = new ClassPathResource(arquivoJarperString);
		try {
			File arquivo = new File(nomeArquivo);
			if(arquivo.exists()) {
				if (arquivo.delete()) {
	                System.out.println("Arquivo excluído com sucesso.");
	            } else {
	                System.out.println("Falha ao excluir o arquivo.");
	            }
			}
			
			InputStream arquivoJarper = resource.getInputStream();
			JasperReport jr = JasperCompileManager.compileReport(arquivoJarper);
			
			//Criar os subRelatorios
			
//			InputStream arquivoJarperSubReportPizza = resource.getInputStream();
//			JasperReport jrSubReportPizza = JasperCompileManager.compileReport(arquivoJarperSubReportPizza);
//			
//			InputStream arquivoJarperSubReportBebida = resource.getInputStream();
//			JasperReport jrSubReportBebida = JasperCompileManager.compileReport(arquivoJarperSubReportBebida);
//			
//			params.put("SUB_REPORT_PIZZA", jrSubReportPizza);
//			params.put("SUB_REPORT_BEBIDA", jrSubReportBebida);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jr, params
					, new JRBeanCollectionDataSource(pedidoDTO.getItensPedido()));
			
			JRPdfExporter jrPdfExporter = new JRPdfExporter();
			jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			
			jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(nomeArquivo));
			jrPdfExporter.exportReport();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
		//funcionando, descomentar quando estiver tudo pronto
		if(parametros.isImprimir()) {
			print(nomeArquivo);			
		}

		return "Imprimindo";
	}
	
}
