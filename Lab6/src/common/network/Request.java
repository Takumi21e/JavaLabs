package common.network;

import common.model.Product;

import java.io.Serializable;

public class Request implements Serializable {

    private CommandType commandType;

    private Product product;

    private Long id;

    private String stringArgument;

    public Request(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringArgument() {
        return stringArgument;
    }

    public void setStringArgument(String stringArgument) {
        this.stringArgument = stringArgument;
    }
}