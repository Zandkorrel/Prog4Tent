package nl.code7.prog4tent_android.domain;

import java.io.Serializable;

/**
 * Created by Whrabbit on 6/13/2017.
 */

public class Rental  implements Serializable {
    private int  inventory_id, customer_id, staff_id;
    private String rental_id;
    private String rental_date;
    private String return_date;
    private String title;
    private String last_update;

    public String getRental_duration() {
        return rental_duration;
    }

    public void setRental_duration(String rental_duration) {
        this.rental_duration = rental_duration;
    }

    private String rental_duration;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String  getRental_id() {
        return rental_id;
    }

    public void setRental_id(String rental_id) {
        this.rental_id = rental_id;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getRental_date() {
        return rental_date;
    }

    public void setRental_date(String rental_date) {
        this.rental_date = rental_date;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

}
