package dev.pedrohfreitas.genericstore.service;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.pedrohfreitas.genericstore.configs.TenantLocalStorage;
import dev.pedrohfreitas.genericstore.dto.ProductAddedDTO;
import dev.pedrohfreitas.genericstore.dto.ProductIngredientDTO;
import dev.pedrohfreitas.genericstore.dto.ProductItemDTO;
import dev.pedrohfreitas.genericstore.dto.ProductItemFlavorDTO;
import dev.pedrohfreitas.genericstore.dto.ProductItemFlavorSizePriceDTO;
import dev.pedrohfreitas.genericstore.dto.ProductSizeDTO;
import dev.pedrohfreitas.genericstore.dto.ProductTypeDTO;
import dev.pedrohfreitas.genericstore.models.product.ProductAdded;
import dev.pedrohfreitas.genericstore.models.product.ProductIngredient;
import dev.pedrohfreitas.genericstore.models.product.ProductItem;
import dev.pedrohfreitas.genericstore.models.product.ProductItemFlavor;
import dev.pedrohfreitas.genericstore.models.product.ProductItemFlavorSizePrice;
import dev.pedrohfreitas.genericstore.models.product.ProductSize;
import dev.pedrohfreitas.genericstore.models.product.ProductType;
import dev.pedrohfreitas.genericstore.repository.product.ProductAddedRepository;
import dev.pedrohfreitas.genericstore.repository.product.ProductIngredientRepository;
import dev.pedrohfreitas.genericstore.repository.product.ProductItemFlavorRepository;
import dev.pedrohfreitas.genericstore.repository.product.ProductItemFlavorSizePriceRepository;
import dev.pedrohfreitas.genericstore.repository.product.ProductItemRepository;
import dev.pedrohfreitas.genericstore.repository.product.ProductSizeRepository;
import dev.pedrohfreitas.genericstore.repository.product.ProductTypeRepository;
import dev.pedrohfreitas.genericstore.util.CRUDBasicUtil;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

	private final ModelMapper mapper = new ModelMapper();
	
	private final CRUDBasicUtil crudBasicUtil;

	private final ProductAddedRepository productAddedRepository;
	private final ProductIngredientRepository productIngredientRepository;
	private final ProductItemFlavorRepository productItemFlavorRepository;
	private final ProductItemFlavorSizePriceRepository productItemFlavorSizePriceRepository;
	private final ProductItemRepository productItemRepository;
	private final ProductSizeRepository productSizeRepository;
	private final ProductTypeRepository productTypeRepository;

	public ProductService(CRUDBasicUtil crudBasicUtil,
			ProductAddedRepository productAddedRepository,
			ProductIngredientRepository productIngredientRepository,
			ProductItemFlavorRepository productItemFlavorRepository,
			ProductItemFlavorSizePriceRepository productItemFlavorSizePriceRepository,
			ProductItemRepository productItemRepository, ProductSizeRepository productSizeRepository,
			ProductTypeRepository productTypeRepository) {
		this.crudBasicUtil = crudBasicUtil;
		this.productAddedRepository = productAddedRepository;
		this.productIngredientRepository = productIngredientRepository;
		this.productItemFlavorRepository = productItemFlavorRepository;
		this.productItemFlavorSizePriceRepository = productItemFlavorSizePriceRepository;
		this.productItemRepository = productItemRepository;
		this.productSizeRepository = productSizeRepository;
		this.productTypeRepository = productTypeRepository;

	}

	@Transactional
	public ProductTypeDTO createProductType(ProductTypeDTO productTypeDTO) {
		ProductType productType = mapper.map(productTypeDTO, ProductType.class);
		productType = productTypeRepository.save(productType);
		return mapper.map(productType, ProductTypeDTO.class);
	}

	public <T> Page<T> getAll(Class<T> dtoClass, T dto, Pageable pageable) {
//		return crudBasicUtil.getAll(dtoClass, dto, pageable);
		return null;
	}

	public ProductTypeDTO getProductTypesById(UUID id) {
		Optional<ProductType> item = productTypeRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id);
		return mapper.map(item.get(), ProductTypeDTO.class);
	}

	public ProductTypeDTO putProductChildById(UUID id, ProductTypeDTO dado) {
		ProductType item = productTypeRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id).get();
		item = mapper.map(dado, ProductType.class);
		return mapper.map(productTypeRepository.save(item), ProductTypeDTO.class);
	}

	@Transactional
	public ProductSizeDTO createProductSize(ProductSizeDTO productSizeDTO) {
		ProductSize productSize = mapper.map(productSizeDTO, ProductSize.class);
		productSize = productSizeRepository.save(productSize);
		return mapper.map(productSize, ProductSizeDTO.class);
	}

	public ProductSizeDTO getProductSizeById(UUID id) {
		Optional<ProductSize> item = productSizeRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id);
		return mapper.map(item.get(), ProductSizeDTO.class);
	}

	public ProductSizeDTO putProductChildById(UUID id, ProductSizeDTO dado) {
		ProductSize item = productSizeRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id).get();
		item = mapper.map(dado, ProductSize.class);
		return mapper.map(productSizeRepository.save(item), ProductSizeDTO.class);
	}

	@Transactional
	public ProductItemDTO createProductItem(ProductItemDTO productItemDTO) {
		ProductItem productItem = mapper.map(productItemDTO, ProductItem.class);
		productItem = productItemRepository.save(productItem);
		return mapper.map(productItem, ProductItemDTO.class);
	}

	public ProductItemDTO getProductItemById(UUID id) {
		Optional<ProductItem> item = productItemRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id);
		return mapper.map(item.get(), ProductItemDTO.class);
	}

	public ProductItemDTO putProductChildById(UUID id, ProductItemDTO dado) {
		ProductItem item = productItemRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id).get();
		item = mapper.map(dado, ProductItem.class);
		return mapper.map(productItemRepository.save(item), ProductItemDTO.class);
	}

	@Transactional
	public ProductItemFlavorDTO createProductItemFlavor(ProductItemFlavorDTO productItemFlavorDTO) {
		ProductItemFlavor productItemFlavor = mapper.map(productItemFlavorDTO, ProductItemFlavor.class);
		productItemFlavor = productItemFlavorRepository.save(productItemFlavor);
		return mapper.map(productItemFlavor, ProductItemFlavorDTO.class);
	}

	public ProductItemFlavorDTO getProductItemFlavorById(UUID id) {
		Optional<ProductItemFlavor> item = productItemFlavorRepository
				.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id);
		return mapper.map(item.get(), ProductItemFlavorDTO.class);
	}

	public ProductItemFlavorDTO putProductChildById(UUID id, ProductItemFlavorDTO dado) {
		ProductItemFlavor item = productItemFlavorRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id).get();
		item = mapper.map(dado, ProductItemFlavor.class);
		return mapper.map(productItemFlavorRepository.save(item), ProductItemFlavorDTO.class);
	}

	@Transactional
	public ProductItemFlavorSizePriceDTO createProductItemFlavorSizePrice(
			ProductItemFlavorSizePriceDTO productItemFlavorSizePriceDTO) {
		ProductItemFlavorSizePrice productItemFlavorSizePrice = mapper.map(productItemFlavorSizePriceDTO,
				ProductItemFlavorSizePrice.class);
		productItemFlavorSizePrice = productItemFlavorSizePriceRepository.save(productItemFlavorSizePrice);
		return mapper.map(productItemFlavorSizePrice, ProductItemFlavorSizePriceDTO.class);
	}

	public ProductItemFlavorSizePriceDTO getProductItemFlavorSizePriceById(UUID id) {
		Optional<ProductItemFlavorSizePrice> item = productItemFlavorSizePriceRepository
				.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id);
		return mapper.map(item.get(), ProductItemFlavorSizePriceDTO.class);
	}

	public ProductItemFlavorSizePriceDTO productAddedRepository(UUID id, ProductItemFlavorSizePriceDTO dado) {
		ProductItemFlavorSizePrice item = productItemFlavorSizePriceRepository
				.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id).get();
		item = mapper.map(dado, ProductItemFlavorSizePrice.class);
		return mapper.map(productItemFlavorSizePriceRepository.save(item), ProductItemFlavorSizePriceDTO.class);
	}

	@Transactional
	public ProductAddedDTO createProductAdded(ProductAddedDTO productAddedDTO) {
		ProductAdded productAdded = mapper.map(productAddedDTO, ProductAdded.class);
		productAdded = productAddedRepository.save(productAdded);
		return mapper.map(productAdded, ProductAddedDTO.class);
	}

	public ProductAddedDTO getProductAddedById(UUID id) {
		Optional<ProductAdded> item = productAddedRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(),
				id);
		return mapper.map(item.get(), ProductAddedDTO.class);
	}

	public ProductAddedDTO putProductChildById(UUID id, ProductAddedDTO dado) {
		ProductAdded item = productAddedRepository.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id).get();
		item = mapper.map(dado, ProductAdded.class);
		return mapper.map(productAddedRepository.save(item), ProductAddedDTO.class);
	}

	@Transactional
	public ProductIngredientDTO createProductIngredient(ProductIngredientDTO productIngredientDTO) {
		ProductIngredient productIngredient = mapper.map(productIngredientDTO, ProductIngredient.class);
		productIngredient = productIngredientRepository.save(productIngredient);
		return mapper.map(productIngredient, ProductIngredientDTO.class);
	}

	public ProductIngredientDTO getProductIngredientById(UUID id) {
		Optional<ProductIngredient> item = productIngredientRepository
				.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id);
		return mapper.map(item.get(), ProductIngredientDTO.class);
	}

	public ProductIngredientDTO putProductChildById(UUID id, ProductIngredientDTO dado) {
		ProductIngredient item = productIngredientRepository
				.findAllByTenantIDAndId(TenantLocalStorage.getTenantID(), id).get();
		item = mapper.map(dado, ProductIngredient.class);
		return mapper.map(productIngredientRepository.save(item), ProductIngredientDTO.class);
	}

}
