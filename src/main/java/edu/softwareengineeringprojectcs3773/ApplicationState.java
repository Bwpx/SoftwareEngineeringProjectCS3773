package edu.softwareengineeringprojectcs3773;

import edu.softwareengineeringprojectcs3773.model.Account;
import edu.softwareengineeringprojectcs3773.model.Cart;
import edu.softwareengineeringprojectcs3773.model.Item;
import edu.softwareengineeringprojectcs3773.service.ItemService;


public final class ApplicationState {

    private static final ItemService ITEM_SERVICE =
            new ItemService();

    private static Account currentAccount;
    private static Cart currentCart;

    private ApplicationState() {
    }

    public static ItemService getItemService() {
        return ITEM_SERVICE;
    }

    public static Account getCurrentAccount() {
        return currentAccount;
    }

    public static void setCurrentAccount(Account account) {
        currentAccount = account;

        if (account == null) {
            currentCart = null;
            return;
        }

        currentCart = new Cart (account);
    }

    public static Cart getCurrentCart() {
        if (currentCart == null && currentAccount != null) {
            currentCart = new Cart(currentAccount);
        }

        return currentCart;
    }

    public static boolean isLoggedIn() {
        return currentAccount != null;
    }

    public static void clearSession() {
        currentAccount = null;
        currentCart = null;
    }
}
