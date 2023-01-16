package ua.com.alevel.db;

import ua.com.alevel.entity.Shop;
import ua.com.alevel.entity.Сustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DbStorage {

    private List<Сustomer> сustomers = new ArrayList<>();
    private List<Shop> shops = new ArrayList<>();
    private static DbStorage instance;



    public static DbStorage getInstance() {
        if (instance == null) {
            instance = new DbStorage();
        }
        return instance;
    }

    public void clean() {
        сustomers = new ArrayList<>();
        shops = new ArrayList<>();
    }

    private String generateСustomerId() {
        String id = UUID.randomUUID().toString();
        if (сustomers.stream().anyMatch(customer -> customer.getId().equals(id))) {
            return generateСustomerId();
        }
        return id;
    }

    public String addСustomer(Сustomer сustomer) {
        String id = generateСustomerId();
        сustomer.setId(id);
        сustomers.add(сustomer);
        return id;
    }

    private String generateShopId() {
        String id = UUID.randomUUID().toString();
        if (shops.stream().anyMatch(customer -> customer.getId().equals(id))) {
            return generateСustomerId();
        }
        return id;
    }

    public String addShop(Shop shop) {
        String id = generateShopId();
        shop.setId(id);
        shops.add(shop);
        return id;
    }

    public List<Сustomer> findAllCustomers() {
        return new ArrayList<>(сustomers);
    }

    public List<Shop> findAllShops() {
        return new ArrayList<>(shops);
    }

    public Optional<Сustomer> getCustomer(String id) {
        return сustomers.
                stream().
                filter(Сustomer -> Сustomer.getId().equals(id)).
                findFirst();
    }

    public Optional<Shop> getShop(String id) {
        return shops.
                stream().
                filter(shop -> shop.getId().equals(id)).
                findFirst();
    }

    public void attach(String customerId, String shopId) {
        attachCustomerToShop(customerId, shopId);
        attachShopToCustomer(customerId, shopId);
    }

    private void attachCustomerToShop(String customerId, String shopId) {
        getShop(shopId).ifPresent(it -> {
            List<String> customers = (List<String>) it.getCustomerIdList();
            customers.add(customerId);
        });
    }

    private void attachShopToCustomer(String customerId, String shopId) {
        getCustomer(customerId).ifPresent(it -> {
            List<String> shops = (List<String>) it.getShopIdList();
            shops.add(shopId);
        });
    }

    public void updateCustomer(Сustomer сustomer) {
        сustomers.remove(сustomer);
        сustomers.add(сustomer);
    }

    public void updateShop(Shop shop) {
        shops.remove(shop);
        shops.add(shop);
    }

    public void deleteCustomer(String id)
    {
        сustomers.removeIf(customer -> customer.getId().equals(id));
    }

    public void deleteShop(String id) {

        shops.removeIf(shop -> shop.getId().equals(id));
    }

    public void deleteCustomerFromShopList(String id) {
        getShop(id).ifPresent(shop -> {
            shop.getCustomerIdList().removeIf(customer -> id.equals(customer));
        });
    }

    public void deleteShopFromCustomerList(String id) {
        getCustomer(id).ifPresent(customer -> {
            customer.getShopIdList().removeIf(shop -> id.equals(shop));
        });
    }


    public void createСustomer(Сustomer customer) {
    }

    public void createShop(Shop shop) {
    }
}