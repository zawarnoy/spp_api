package spp.lab.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends SafeDeleteEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String login;
    private String password;
    private String apiKey;


    @OneToOne(targetEntity = UserSubscription.class, mappedBy = "user")
    @JsonManagedReference
    private UserSubscription subscription;


    public User()
    {}

    public User(String username, String login, String password, String apiKey, Role role, User trainer)
    {
        this.username = username;
        this.login = login;
        this.password = password;
        this.apiKey = apiKey;
        this.role = role;
        this.state = (State.ACTIVE);
        this.trainer = trainer;
    }

    public User(String username, String login, String password, String apiKey, Role role)
    {
        this.username = username;
        this.login = login;
        this.password = password;
        this.apiKey = apiKey;
        this.role = role;
        this.state = (State.ACTIVE);
        this.trainer = null;
    }


    @ManyToOne(targetEntity = User.class)
    @JsonManagedReference
    private User trainer;

    @OneToMany(targetEntity = User.class, mappedBy = "trainer")
    @JsonBackReference
    private Set<User> coached;

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public Set<User> getCoached() {
        return coached;
    }

    public void setCoached(Set<User> coached) {
        this.coached = coached;
    }


    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Role role;


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserSubscription getSubscription() {
        return subscription;
    }

    public void setSubscription(UserSubscription subscription) {
        this.subscription = subscription;
    }
}