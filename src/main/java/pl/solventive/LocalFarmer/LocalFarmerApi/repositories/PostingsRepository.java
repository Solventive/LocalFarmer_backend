package pl.solventive.LocalFarmer.LocalFarmerApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.LFUser;
import pl.solventive.LocalFarmer.LocalFarmerApi.entities.Posting;

@Component
public interface PostingsRepository extends JpaRepository<Posting, Integer> {

    @Query("SELECT p FROM postings p WHERE p.userId = ?1")
    LFUser findPostingsByUserId(String name);

    @Query("SELECT p FROM postings p WHERE p.id = ?1")
    LFUser getPosting(int id);
}
