package org.oplapp.repository;

import org.oplapp.model.*;
import org.springframework.data.jpa.repository.*;

/**
 * Repository interface providing tag related CRUD operations.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
}
