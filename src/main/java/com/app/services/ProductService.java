package com.app.services;

import com.app.dto.ProductFilter;
import com.app.entities.Category;
import com.app.entities.OrderItem;
import com.app.entities.Product;
import com.app.repository.CategoryRepository;
import com.app.repository.OrderItemRepository;
import com.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderItemRepository orderItemRepository;

    public List<Product> search(ProductFilter search) {
        // TODO Create query to search by title and description on query attribute, search on category title when category attribute is not null

        return productRepository.searchProductsByQueryAndCategory(
                !search.getQuery().isBlank() ? search.getQuery() : null,
                !search.getCategory().isBlank() ? search.getCategory() : null,
                search.getSale(),
                search.getBrands().isBlank() ? null : Arrays.stream(search.getBrands().split(",")).toList()
        );
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product>getLatestProducts(){
        return productRepository.findLatest();
    }

    public Optional<Product> getProductByTittle(String title){
        return productRepository
                .findProductsByTitleIgnoreCase(title);
    }
    public ResponseEntity<?> create(Product product){
        if (product.isOnSale()){
            product.setDiscountPrice(product.getPrice()-(product.getPrice()*0.2));
        }
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<?> update(Product product){
        Product productUpdate = productRepository.findById(product.getId())
                .orElseThrow(()->new IllegalStateException
                        ("Product with id: "+product.getId()+" does not exist"));

        if (product.getTitle().equals(productUpdate.getTitle())){
            return ResponseEntity.badRequest().body("This tittle already exist.");
        }
        if (product.getTitle() == null || product.getTitle().isBlank()){
            return ResponseEntity.badRequest().body("Tittle cannot be null");
        }
        productUpdate.setBrand(product.getBrand());
        productUpdate.setColor(product.getColor());
        productUpdate.setTitle(product.getTitle());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setOnSale(product.isOnSale());
        productRepository.save(productUpdate);
        return ResponseEntity.ok("Product successfully updated");
    }

    public ResponseEntity<?> delete(UUID id){
        Product optionlaProduct = productRepository.findById(id)
                .orElseThrow(()->new IllegalStateException("Product with id: "+id+" does not exist."));
        productRepository.deleteById(optionlaProduct.getId());
        return ResponseEntity.ok("Product deleted successfully");
    }

    public void createDefaultProducts() {
        Category smartphones = categoryRepository.findByTitleIgnoreCase("Smartphones")
              .orElseThrow(()->new IllegalStateException("Category does not exist."));
        Category TVs = categoryRepository.findByTitleIgnoreCase("TV-s")
                .orElseThrow(()->new IllegalStateException("Category does not exist."));

        productRepository.findProductsByTitleIgnoreCase("iPhone 12 5G 64GB Black").orElseGet(() -> {
            Product item = Product.builder()
                    .title("iPhone 12 5G 64GB Black")
                    .brand("Apple")
                    .color("black")
                    .categoryId(smartphones.getId())
                    .description("Kamera: 12 MP\n" +
                            "Marka: APPLE\n" +
                            "Ekrani: 6.1\"\n" +
                            "Tipi i ekranit:\tSuper Retina XDR OLED\n" +
                            "RAM: 4 GB\n" +
                            "Memorja: 64 gb\n" +
                            "Procesori: HEXA-CORE")
                    .price(79990)
                    .onSale(true)
                    .productImage1("https://www.neptun.al/2020/10/29/TL3205.JPG")
                    .productImage2("https://www.neptun.al/2021/03/03/TL3205_8.JPG")
                    .productImage3("https://www.neptun.al/2021/03/03/TL3205_7.JPG")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }


            productRepository.save(item);
            log.info("Product created");
            return item;
        });
        productRepository.findProductsByTitleIgnoreCase("TELEVIZOR LED SAMSUNG 50 4K UE50AU7092UXXH").orElseGet(() -> {
            Product item = Product.builder()
                    .title("TELEVIZOR LED SAMSUNG 50 4K UE50AU7092UXXH")
                    .brand("Samsung")
                    .color("black")
                    .categoryId(TVs.getId())
                    .description("Marka: Samsung\n" +
                            "Modeli: UE50AU7092UXXH\n" +
                            "Procesor: Quad Core\n" +
                            "Madhesia Ekranit: 50\"\n" +
                            "Tipi i Panelit: Crystal LED\n" +
                            "Frekuenca: 2000 PQI\n" +
                            "Rezolucion Ekrani: Ultra HD 4K (3840x2160)\n" +
                            "Kontrasti: Mega Kontrast, HDR 10+, HLG, UHD Dimming.")
                    .price(69990)
                    .onSale(false)
                    .productImage1("https://globe.al/images/detailed/355/1_piun-mt.png")
                    .productImage2("https://globe.al/images/thumbnails/900/600/detailed/355/2_bruj-wg.png")
                    .productImage3("https://globe.al/images/detailed/355/1_piun-mt.png")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });

        productRepository.findProductsByTitleIgnoreCase("Xiaomi Redmi A1 2/32GB Black").orElseGet(() -> {
            Product item = Product.builder()
                    .title("Xiaomi Redmi A1 2/32GB Black")
                    .brand("Xiaomi")
                    .color("black")
                    .categoryId(smartphones.getId())
                    .description("Kamera:\t8 MP + 0.08 MP\n" +
                            "Video:\t1080p@30fps\n" +
                            "Kamera dytesore:\t5 MP\n" +
                            "Procesori Ghz:\tARM Cortex-A53, 2.0 Ghz\n" +
                            "Madhesia e Ekranit:\t6.52 Inch\n" +
                            "Sistemi Operativ:\tANDROID")
                    .price(13990)
                    .onSale(true)
                    .productImage1("https://www.neptun.al/2022/10/25/TL3774.JPG")
                    .productImage2("https://www.neptun.al/2022/10/25/TL3774_4.JPG")
                    .productImage3("https://www.neptun.al/2022/10/25/TL3774_1.JPG")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });
        productRepository.findProductsByTitleIgnoreCase("Samsung Galaxy S23 8/128GB Cream").orElseGet(() -> {
            Product item = Product.builder()
                    .title("Samsung Galaxy S23 8/128GB Cream")
                    .brand("Samsung")
                    .color("cream")
                    .categoryId(smartphones.getId())
                    .description("Marka:\tSAMSUNG\n" +
                            "Ekrani:\t6.1\"\n" +
                            "Tipi i ekranit:\tDynamic AMOLED 2X\n" +
                            "RAM:\t8 GB\n" +
                            "Memorja:\t128 GB\n" +
                            "Procesori:\tOcta-Core")
                    .price(109990)
                    .onSale(true)
                    .productImage1("https://www.neptun.al/2023/02/01/TL3786.JPG")
                    .productImage2("https://www.neptun.al/2023/02/01/TL3786_4.JPG")
                    .productImage3("https://www.neptun.al/2023/02/01/TL3786_3.JPG")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });
        productRepository.findProductsByTitleIgnoreCase("TV SONY LED XR85X95KAEP").orElseGet(() -> {
            Product item = Product.builder()
                    .title("TV SONY LED XR85X95KAEP")
                    .brand("SONY")
                    .color("black")
                    .categoryId(TVs.getId())
                    .description("Marka: Sony\n" +
                            "Modeli: XR85X95KAEP\n" +
                            "Procesor: Quad Core\n" +
                            "Madhesia Ekranit: 85\"\n" +
                            "Tipi i Panelit: OLED LED\n" +
                            "Frekuenca: 4000 PQI\n" +
                            "Rezolucion Ekrani: Ultra HD 4K (3840x2160)\n" +
                            "Kontrasti: Mega Kontrast, HDR 10+, HLG, UHD Dimming.")
                    .price(459990)
                    .onSale(true)
                    .bannerImage("https://www.neptun.al/2022/12/07/TV4955.JPG")
                    .productImage1("https://www.neptun.al/2022/12/07/TV4955.JPG")
                    .productImage2("https://www.neptun.al/2022/12/07/TV4955_3.JPG")
                    .productImage3("https://www.neptun.al/2022/12/07/TV4955_1.JPG")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });
        productRepository.findProductsByTitleIgnoreCase("TELEVIZOR LED PHILIPS 50 4K 50PUS8507/12").orElseGet(() -> {
            Product item = Product.builder()
                    .title("TELEVIZOR LED PHILIPS 50 4K 50PUS8507/12")
                    .brand("Philips")
                    .color("black")
                    .categoryId(TVs.getId())
                    .description("Marka: Philips\n" +
                            "Modeli: 50PUS8507/12\n" +
                            "Procesor: Quad Core\n" +
                            "Madhesia Ekranit: 50\n" +
                            "Tipi i Panelit: LED\n" +
                            "Memorje: 16 GB\n" +
                            "Frekuenca: 60 Hz\n" +
                            "Rezolucion Ekrani: Ultra HD 4K (3840x2160)")
                    .price(99990)
                    .onSale(true)
                    .productImage1("https://globe.al/images/detailed/354/1_-_2022-12-19T122555.512.jpg")
                    .productImage2("https://globe.al/images/detailed/354/1_-_2022-12-19T122612.149.jpg")
                    .productImage3("https://globe.al/images/detailed/354/1_-_2022-12-19T122631.869.jpg")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }


            productRepository.save(item);
            log.info("Product created");
            return item;
        });
        productRepository.findProductsByTitleIgnoreCase("TELEVIZOR LED LG 43 4K 43UP78003LB").orElseGet(() -> {
            Product item = Product.builder()
                    .title("TELEVIZOR LED LG 43 4K 43UP78003LB")
                    .brand("LG")
                    .color("black")
                    .categoryId(TVs.getId())
                    .description("Marka: LG\n" +
                            "Modeli: 43UP78003LB\n" +
                            "Procesor: Quad Core\n" +
                            "Madhesia Ekranit: 43\n" +
                            "Tipi i Panelit: LED\n" +
                            "Frekuenca: 50 Hz\n" +
                            "Rezolucioni: 3840х2160 4K UHD)\n" +
                            "Kontrasti: HDR10 Pro, Hybrid Log Gamma, Image Enhancing")
                    .price(66990)
                    .onSale(false)
                    .productImage1("https://globe.al/images/detailed/355/1_piun-mt.png")
                    .productImage2("https://globe.al/images/thumbnails/900/600/detailed/355/2_bruj-wg.png")
                    .productImage3("https://globe.al/images/detailed/355/1_piun-mt.png")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });

        productRepository.findProductsByTitleIgnoreCase("CELULAR SAMSUNG GALAXY A34 DUAL 128GB").orElseGet(() -> {
            Product item = Product.builder()
                    .title("CELULAR SAMSUNG GALAXY A34 DUAL 128GB")
                    .brand("SAMSUNG")
                    .color("silver")
                    .categoryId(smartphones.getId())
                    .description("Marka: Samsung\n" +
                            "Modeli: SM-A346B\n" +
                            "Network: 5G, Wi-Fi\n" +
                            "Karte SIM: Dual SIM\n" +
                            "Model SIM: Nano SIM + Hybrid SIM\n" +
                            "Teknologji Ekrani: Super AMOLED\n" +
                            "Madhesi Ekrani: 6.6\" FHD+ 120 Hz\n" +
                            "Kamera: 48 MP OIS F1.8, + 8 MP F2.2 + 5 MP F2.4\n" +
                            "Procesor: Octa-core")
                    .price(43990)
                    .onSale(true)
                    .productImage1("https://globe.al/images/detailed/355/1_hshc-4p.jpg")
                    .productImage2("https://globe.al/images/detailed/355/3_dnss-fp.jpg")
                    .productImage3("https://globe.al/images/detailed/355/4_gk9p-sy.jpg")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });
        productRepository.findProductsByTitleIgnoreCase("APPLE IPHONE 14 128GB 3L238Z/A BLUE").orElseGet(() -> {
            Product item = Product.builder()
                    .title("APPLE IPHONE 14 128GB 3L238Z/A BLUE")
                    .brand("Apple")
                    .color("Blue")
                    .categoryId(smartphones.getId())
                    .description("Marka: Apple\n" +
                            "Modeli: 3l244z/a\n" +
                            "Model SIM: Nano SIM/eSIM\n" +
                            "Teknologji Ekrani: Super Retina XDR Display\n" +
                            "Madhesi Ekrani: 6.1\"\n" +
                            "Kamera: 12 MP + 12 MP\n" +
                            "Kamera Sekondare: 12 MP\n" +
                            "Procesor: A15 Bionic ( 2x3.23 GHz )\n" +
                            "Sistemi Operativ: iOS 16.0\n" +
                            "Memorje: 128 GB")
                    .price(105990)
                    .onSale(false)
                    .productImage1("https://globe.al/images/detailed/356/1_t7vu-0n.jpg")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });
        productRepository.findProductsByTitleIgnoreCase("TELEVIZOR QLED TCL 50 4K 50C645").orElseGet(() -> {
            Product item = Product.builder()
                    .title("TELEVIZOR QLED TCL 50 4K 50C645")
                    .brand("TCL")
                    .color("black")
                    .categoryId(TVs.getId())
                    .description("Marka: TCL\n" +
                            "Modeli: 50C646\n" +
                            "Procesor: Quad Core\n" +
                            "Madhesia Ekranit: 50\"\n" +
                            "Tipi i Panelit: QLED\n" +
                            "Memorje: 16 GB + 2 DDR\n" +
                            "Frekuenca: 60 Hz\n" +
                            "Rezolucioni: UHD, 4K, 3840x2160\n" +
                            "Kontrasti: HDR 10, HDR 10+, Dolby Vision, HLG")
                    .price(77990)
                    .onSale(true)
                    .productImage1("https://globe.al/images/detailed/356/1_wvzw-dj.jpg")
                    .productImage2("https://globe.al/images/detailed/356/2_wnzd-us.jpg")
                    .productImage3("https://globe.al/images/detailed/356/3_cybw-dm.jpg")
                    .build();
            if (item.isOnSale()){
                item.setDiscountPrice(item.getPrice()-(item.getPrice()*0.2));
            }

            productRepository.save(item);
            log.info("Product created");
            return item;
        });
    }

    public List<Product> getMostSellingProducts() {
        return productRepository.findMostSoldProducts();
    }

    public Product getById(UUID id) {
        return productRepository.findById(id).orElseThrow();
    }
}
