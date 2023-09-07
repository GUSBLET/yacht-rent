package com.yachtrent.main.harbor;

import com.yachtrent.main.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HarborRepository extends JpaRepository<Harbor, Long> {
}
