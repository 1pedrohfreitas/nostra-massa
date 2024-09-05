export class DefaultField{
    label : string = '';
    name :  string = '';
}
export class ProductType{
    id ?: string;
    created ?: string;
    createdBy ?: string;
    updated ?: string;
    updatedBy ?: string;
    tenantID ?: string;
    actived ?: boolean;
    type ?: string = '';
    description ?: string;
    itens ?: ProductItem[] = [];
}

export class ProductSize{
    id ?: string;
    created ?: string;
    createdBy ?: string;
    updated ?: string;
    updatedBy ?: string;
    tenantID ?: string;
    size ?: string;
    flavors ?: ProductItemFlavor[] = [];

}
export class ProductItemFlavorSizePrice{
    id ?: string;
    created ?: string;
    createdBy ?: string;
    updated ?: string;
    updatedBy ?: string;
    tenantID ?: string;
    actived ?: boolean;
    price ?: number;
    flavor ?: ProductItemFlavor;

}
export class ProductItemFlavor{
    i ?: string;
    created ?: string;
    createdBy ?: string;
    updated ?: string;
    updatedBy ?: string;
    tenantID ?: string;
    flavor ?: string;
    productItens : ProductItem[] = [];
    ingredients : ProductIngredient[] = [];
    addeds : ProductAdded[] = [];
    sizes : ProductSize[] = [];
    prices : ProductItemFlavorSizePrice[] = [];

}
export class ProductItem{
    id ?: string;
    created ?: string;
    createdBy ?: string;
    updated ?: string;
    updatedBy ?: string;
    tenantID ?: string;
    actived ?: boolean;
    item ?: string;
    description ?: string;
    maxAmountFlavor ?: number;
    productType ?: ProductType;
    flavors : ProductItemFlavor[] = [];

}
export class ProductIngredient{
    i ?: string;
    created ?: string;
    createdBy ?: string;
    updated ?: string;
    updatedBy ?: string;
    tenantID ?: string;
    ingredient ?: string;
    flavors : ProductItemFlavor[] = [];
}
export class ProductAdded{
    i ?: string;
    created ?: string;
    createdBy ?: string;
    updated ?: string;
    updatedBy ?: string;
    tenantID ?: string;
    actived ?: boolean;
    added ?: string;
    description ?: string;
    flavors : ProductItemFlavor[] = [];
}