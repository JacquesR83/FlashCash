package jr.dev.FlashCash.repository;

import jr.dev.FlashCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {

    // Methode 1 - JPA magic => by the name of our method, SQL request is made by JPA
    Optional<User> findUserByEmail(String s);

//    // Methode 2 - HQL => pointe sur l'objet du code, et pas la base directement
//    // Trouver un utilisateur par email avec une requête JPQL (recherche dans la base de données)
//    @Query("SELECT u FROM User u WHERE u.email = ?1")
//    public User findUserByEmail(String email);
//
//    //Methode 3 -SQL Natif => vrai SQL
//    // Utiliser SQL natif (par exemple, avec un alias pour une table complexe ou un join)
//    @Query(value = "SELECT * FROM user WHERE email = ?1", nativeQuery = true)
//    public User findUserByEmailNative(String email);

}
