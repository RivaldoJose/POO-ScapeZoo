public class Item {
    private String itemDescription;
    private String itemWeight;

    public Item(String itemDescription, String itemWeight) {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    public String getItemDetails() {
        return "Descrição: " + itemDescription + "       Peso: " + itemWeight;
    }
}
