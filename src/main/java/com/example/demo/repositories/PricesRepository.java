package com.example.demo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Prices;

@Repository
public interface PricesRepository extends CrudRepository<Prices, Integer> {

    @Query(
        // @formatter:off
        "from Prices p join fetch p.brand b join fetch p.product pt " +
        "where b.id = ?1 and pt.id = ?2 and ?3 between p.startDate and p.endDate " +
        "order by p.priority desc"
        // @formatter:on
    )
    List<Prices> findFirstOrderByPriorityDesc(Integer brandId, Integer productId, Date requestDate, Pageable pageable);

}
