package hansung.hansung_connect.domain.link.service;

import hansung.hansung_connect.common.exception.GeneralException;
import hansung.hansung_connect.common.exception.code.status.ErrorStatus;
import hansung.hansung_connect.domain.link.converter.LinkConverter;
import hansung.hansung_connect.domain.link.dto.LinkRequestDTO;
import hansung.hansung_connect.domain.link.dto.LinkResponseDTO;
import hansung.hansung_connect.domain.link.entity.Link;
import hansung.hansung_connect.domain.link.repository.LinkRepository;
import hansung.hansung_connect.domain.user.entity.User;
import hansung.hansung_connect.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public LinkResponseDTO.LinkResultDTO createLink(Long userId, LinkRequestDTO.CreateLinkDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Link link = LinkConverter.toLink(request, user);
        Link saved = linkRepository.save(link);
        return LinkConverter.toLinkResultDTO(saved);
    }
}

