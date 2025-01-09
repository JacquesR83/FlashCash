package jr.dev.FlashCash.interfaces.repository;

import jr.dev.FlashCash.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer>,JpaSpecificationExecutor<User>{

    // Methode 1 - JPA magic => by the name of our method, SQL request is made by JPA
    Optional<User> findUserByEmail(String s);




    boolean existsByEmail(String email);





    //EXAMPLES OF STOCKED PROCEDURE
    // JPA can't execute 2 requests at the same time for a stocked procedure
    // 1) Executes Stocked Procedure passing email and saving password in "@userPassword" variable in DB
    @Query(value = "CALL PS_Password(:email, @userPassword);", nativeQuery = true)
    void callPasswordProcedure(@Param("email") String email);

    //2) Get password already stocked in the defined stocked procedure variable "@userPassword" (this one stays as long as user is connected)
    @Query(value = "SELECT @userPassword", nativeQuery = true)
    String getPasswordFromStoredProcedure();




//    @Query(value= "CALL PS_Links(:userId)", nativeQuery = true)
//    List<String> findLinks(Integer id);


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
