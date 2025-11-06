package hansung.hansung_connect.domain.link.service;

import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;

public interface LinkQueryService {
    LinkResponseDTO.LinkResultDTO getLink(Long linkId);
}
