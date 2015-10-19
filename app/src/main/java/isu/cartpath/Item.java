package isu.cartpath;

class Item {
    private boolean inCart;
    private String name;

    Item(String name, boolean inCart) {
        this.name = name;
        this.inCart = inCart;
    }
}
