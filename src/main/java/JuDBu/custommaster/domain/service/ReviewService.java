package JuDBu.custommaster.domain.service;

import JuDBu.custommaster.domain.entity.Review;
import JuDBu.custommaster.domain.dto.review.ReviewDto;
import JuDBu.custommaster.domain.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    // 리뷰 저장(작성) 메서드
    public ReviewDto createReview(
            String comment,
            Long orderId, MultipartFile[] images
    ) {
        // TODO : 회원정보 가져와야 리뷰작성 가능
//        Member currentMember = facade.getCurrentMember();

        Review review = Review.builder()
                .comment(comment)
//                .member(currentMember)
                .build();

        // 리뷰 작성시 사진(이미지) 첨부
        for (MultipartFile image : images) {
            String imagePath = saveImage(image);
            imagePath = imagePath.replaceAll("\\\\", "/");
            review.getImages().add(imagePath);
        }


        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    // article 페이지 단위로 받아오기
    public Page<ReviewDto> readReviewPaged(Pageable pageable) {
        Pageable reversePageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("id").descending());

        Page<Review> articlesPage = reviewRepository.findAll(reversePageable);
        List<ReviewDto> articleDtos = articlesPage.getContent().stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());
        return new PageImpl<>(articleDtos, reversePageable, articlesPage.getTotalElements());
    }

    // 특정 articleId의 article 가져오기
    public ReviewDto readArticle(Long articleId) {
        Optional<Review> optionalReview = reviewRepository.findById(articleId);
        // article이 존재하지 않는 경우
//        if (optionalArticle.isEmpty())
//            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);

        Review review = optionalReview.get();
        return ReviewDto.fromEntity(review);
    }

    // article 수정
    public ReviewDto updateReview(
            Long id,
            String comment,
            MultipartFile[] images,
            @RequestParam("accountId")
            Long accountId
    ) {
        // TODO :
        Optional<Review> optionalReview = reviewRepository.findById(accountId);
        // TODO : 존재하지 않는 review 일 경우
//        if (optionalReview.isEmpty())
//            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_NOT_EXISTS);

        Review review = optionalReview.get();
//        Member currentMember = facade.getCurrentMember();
        // TODO : article의 주인이 아닌 경우
//        if (!review.getMember().getId().equals(currentMember.getId())) {
//            log.info(review.getMember().getId().toString());
//            log.info(currentMember.getId().toString());
//            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_FORBIDDEN);
//        }

        // TODO : AccountId 값 받아와야 가능
//        // Review 수정
//        review.setComment(comment);
//        // 기존 이미지 삭제
//        for (String imagePath : review.getImages()) {
//            deleteImage(imagePath);
//        }
//        review.getImages().clear();
//
//        // 새로운 이미지 저장
//        for (MultipartFile image : images) {
//            String imagePath = saveImage(image);
//            imagePath = imagePath.replaceAll("\\\\", "/");
//            review.getImages().add(imagePath);
//        }


        return ReviewDto.fromEntity(reviewRepository.save(review));
    }


    // TODO : 다시 해야함
    public void deleteReview(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        // article이 존재하지 않는 경우
        if (optionalReview.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");

        Review review = optionalReview.get();
//        Member currentMember = facade.getCurrentMember();
        // article의 주인이 아닌 경우
//        if (!reviewId.getMember().getId().equals(currentMember.getId()))
//            throw new GlobalExceptionHandler(CustomGlobalErrorCode.ARTICLE_FORBIDDEN);

        // 기존 이미지 삭제
        for (String imagePath : review.getImages()) {
            deleteImage(imagePath);
        }

        reviewRepository.delete(review);
    }


    // TODO : 나중에 정보 받아와서
//    public Boolean isCurrentMemberLikedArticle(Long articleId) {
//        Member currentMember = facade.getCurrentMember();
//        return articleLikeRepository.existsByMemberAndArticleId(currentMember, articleId);
//    }

    public String saveImage(MultipartFile image) {
        String imgDir = "media/img/articles/";
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path imgPath = Path.of(imgDir + imgName);

        try {
            Files.createDirectories(Path.of(imgDir));
            image.transferTo(imgPath);
            log.info(image.getName());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return imgPath.toString();
    }

    public void deleteImage(String imagePath) {
        try {
            Files.deleteIfExists(Path.of(imagePath));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    /////////////////////////////// 0412

//    private void createTagList(ArticleDto newDto, ArticleDto originalDto){
//        Pattern myPattern = Pattern.compile("#(\\s+)");
//        Matcher matcher = myPattern.matcher(newDto.getContent());
//        Set<String> tags = new HashSet<>();
//        while (matcher.find()){
//            tags.add(matcher.group(1));
//        }
//        saveTag(tags,originalDto);
//    }

//    private void saveTag(Set<String> newTagSet, ArticleDto originalDto) {
//        Article article = articleRepository.findById(originalDto.getId()).orElseThrow();
//        Set<ArticleTag> originalTagSet = article.getTags();
//        System.out.println("orgiinalTagSet사이즈....." + originalTagSet.size());
//
//        for (String tagContent : newTagSet) {
//            Tag result = tagRepository.findTagByContent(tagContent);
//            Tag newTag;
//            // 미등록 태그 -> 태그 추가
//
//            if (result==null) {
//                newTag = new Tag(tagContent);
//                tagRepository.save(newTag);
//            } else {
//                System.out.println("궁금한 태그를 검색하세요"+result.getContent());
//                newTag = result;
//            }
//            ArticleTag newTag = new ArticleTag(article, newTag);
//            if (originalTagSet.isEmpty()) {
//                articleTagRepository.save(newTag);
//            } else {
//                if (!(originalTagSet.contains(newTag))) {
//                    articleTagRepository.save(new ArticleTag(article, newTag));
//                } else {
//                    break;
//                }
//            }
//        }
//    }
}