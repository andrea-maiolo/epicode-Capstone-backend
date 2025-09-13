package andreamaiolo.backend.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue
    private long id;
    private LocalDate checkin;
    private LocalDate checkout;
    @ManyToOne
    private User user;
    @ManyToOne
    private Room room;

    public Booking() {
    }

    public Booking(User user, LocalDate checkout, LocalDate checkin, Room room) {
        this.user = user;
        this.checkout = checkout;
        this.checkin = checkin;
        this.room = room;
    }

    public LocalDate getCheckin() {
        return checkin;
    }

    public void setCheckin(LocalDate checkin) {
        this.checkin = checkin;
    }

    public LocalDate getCheckout() {
        return checkout;
    }

    public void setCheckout(LocalDate checkout) {
        this.checkout = checkout;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

}

