package com.dawnbit.storage.attachments;

import java.util.List;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dawnbit.entity.attachments.Attachments;

/**
 * @author DawnBIT
 */
@Repository
public interface AttachmentRepository extends CrudRepository<Attachments, Long> {

    /**
     * get list of attachments
     *
     * @param attachmentIdList list of attachment Id's
     * @return List
     * @author DB-0007
     */

    @Query("select a from Attachments a where a.id IN(?1)")
    List<Attachments> findAllAttchmentsByIds(List<Long> attachmentIdList);

    /**
     * get list of attachments URI based of ids
     *
     * @param attachmentIdList list of attachment Id's
     * @return List
     * @author DB-0082
     */
    @Query("select a.path from Attachments a where a.id IN(?1)")
    List<String> getAllDownloadURIBasedOnIds(List<Long> attachmentIds);

    /**
     * delete list of attachments based on list of ids
     *
     * @param attachmentIds list of attachment Id's
     * @return List
     * @author DB-0082
     */
    @Transactional
    @Modifying
    @Query("delete from Attachments a where a.id in (?1)")
    void deleteByIdIn(List<Long> attachmentIds);

    @Query("select new com.dawnbit.storage.attachments.AttachmentDTO(a) from Attachments a where a.id IN(?1)")
    List<AttachmentDTO> findAllAttchmentDTOByIds(List<Long> attachmentIdList);

}