package team7.inplace.global.exception;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
    List<ErrorLog> findByIsResolvedFalse();
}
