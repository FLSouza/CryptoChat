package br.com.ucb.cryptochat.model;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by jonathan on 6/8/16.
 */
public class Client implements Serializable {

    private static final long serialVersionUID = 8867939267975052979L;

    private Integer port;
    private String username;
    private InetAddress address;

    public Client(InetAddress address, Integer port) {
        this.port = port;
        this.address = address;
    }

    public Client(InetAddress address, Integer port, String username) {
        this(address, port);
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!port.equals(client.port)) return false;
        return address.equals(client.address);

    }

    @Override
    public int hashCode() {
        int result = port.hashCode();
        result = 31 * result + address.hashCode();
        return result;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        if (this.username != null) {
            return username;
        } else {
            return getAddress().getHostName();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "port=" + port +
                ", username='" + username + '\'' +
                ", address=" + address +
                '}';
    }
}
