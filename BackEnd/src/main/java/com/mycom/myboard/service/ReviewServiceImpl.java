package com.mycom.myboard.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.myboard.dao.ReviewDao;
import com.mycom.myboard.dto.ReviewDto;
import com.mycom.myboard.dto.ReviewFileDto;
import com.mycom.myboard.dto.ReviewParamDto;
import com.mycom.myboard.dto.ReviewResultDto;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	@Autowired
	ReviewDao dao;
	
//	@Autowired
//	GoodDao goodDao;
	
//	private static final String uploadFolder = "upload";
	
	/* for production code */
    //uploadPath = getServletContext().getRealPath("/");
    // C:\spring\BoardFileUploadSpringMVCMybatis\src\main\webapp\resources
	
    /* for eclipse development code */
//    String uploadPath = "D:" + File.separator + "work" + File.separator + "Springboot" + File.separator + "SpringBootVueBoardFileUploadMybatis" 
//            + File.separator + "src" 
//            + File.separator + "main"
//            + File.separator + "resources"
//            + File.separator + "static";
	
	@Value("${app.fileupload.uploadDir}")
    String uploadFolder;
    
    @Value("${app.fileupload.uploadPath}")
    String uploadPath;
    
    private static final int SUCCESS = 1;
    private static final int FAIL = 1;
    
    @Override
    @Transactional //한번에 CRUD가 실행되야하기때문에 선언,  트랜잭션 자체가 하나의 단위의 흐름이라고 생각하면 된다
    public ReviewResultDto reviewInsert(ReviewDto dto, MultipartHttpServletRequest request) {
    	System.out.println("인설트 들어옴");
        ReviewResultDto reviewResultDto = new ReviewResultDto();
        try {
            dao.reviewInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"

            List<MultipartFile> fileList = request.getFiles("file");

            File uploadDir = new File(uploadPath + File.separator + uploadFolder);
            if (!uploadDir.exists()) uploadDir.mkdir();

	        for (MultipartFile part : fileList) {
	
	            int reviewId = dto.getReviewId();
	            
	            String fileName = part.getOriginalFilename();
	            
	            //Random File Id
	            UUID uuid = UUID.randomUUID();
	            
	            //file extension
	            String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
	        
	            String savingFileName = uuid + "." + extension;
	        
	            File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            
	            System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            part.transferTo(destFile);
	        
	            // Table Insert
	            ReviewFileDto reviewFileDto = new ReviewFileDto();
	            reviewFileDto.setReviewId(reviewId);
	            reviewFileDto.setFileName(fileName);
	            reviewFileDto.setFileSize(part.getSize());
	            reviewFileDto.setFileContentType(part.getContentType());
	            String freeFileUrl = uploadFolder + "/" + savingFileName;
	            reviewFileDto.setFileUrl(freeFileUrl);
	            System.out.println("들어온곳: " + reviewFileDto);
	            dao.reviewFileInsert(reviewFileDto);
	        }
	
	        reviewResultDto.setResult(SUCCESS);
	    }catch(IOException e) {
	        e.printStackTrace();
	        reviewResultDto.setResult(FAIL);
	    }
	    return reviewResultDto;
	}
	
	@Override
	@Transactional
	public ReviewResultDto reviewUpdate(ReviewDto dto, MultipartHttpServletRequest request){
	    
	    ReviewResultDto reviewResultDto = new ReviewResultDto();
	
	    
	    try {
	        dao.reviewUpdate(dto);
	
	        List<MultipartFile> fileList = request.getFiles("file");
	        
	        File uploadDir = new File(uploadPath + File.separator + uploadFolder);
	        if (!uploadDir.exists()) uploadDir.mkdir();
	        
	        // 물리 파일 삭제, 첨부파일 여러개 고려
	        List<String> fileUrlList = dao.reviewFileUrlDeleteList(dto.getReviewId());    
	        for(String fileUrl : fileUrlList) {    
	            File file = new File(uploadPath + File.separator, fileUrl);
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	
//	        dao.freeFileDelete(dto.getFreeId()); // 테이블 파일 삭제
	        
	        
	        for (MultipartFile part : fileList) {
	            int reviewId = dto.getReviewId();
	            
	            String fileName = part.getOriginalFilename();
	            
	            //Random File Id
	            UUID uuid = UUID.randomUUID();
	            
	            //file extension
	            String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
	        
	            String savingFileName = uuid + "." + extension;
	        
	            File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            
	            System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            part.transferTo(destFile);
	        
	            // Table Insert
	            ReviewFileDto reviewFileDto = new ReviewFileDto();
	            reviewFileDto.setReviewId(reviewId);
	            reviewFileDto.setFileName(fileName);
	            reviewFileDto.setFileSize(part.getSize());
	            reviewFileDto.setFileContentType(part.getContentType());
	            String freeFileUrl = uploadFolder + "/" + savingFileName;
	            reviewFileDto.setFileUrl(freeFileUrl);
	            System.out.println("updateservice: " + reviewFileDto);
	            dao.reviewFileInsert(reviewFileDto);
	        }
	
	        reviewResultDto.setResult(SUCCESS);
	        
	    }catch(IOException e) {
	        e.printStackTrace();
	        reviewResultDto.setResult(FAIL);
	    }
	    
	    return reviewResultDto;
	}
	
	@Override
	@Transactional
	public ReviewResultDto reviewDelete(int reviewId) {
	    
	    ReviewResultDto reviewResultDto = new ReviewResultDto();
	    
	    try {
	        List<String> fileUrlList = dao.reviewFileUrlDeleteList(reviewId);
	        for(String fileUrl : fileUrlList) {
	            File file = new File(uploadPath + File.separator, fileUrl);                
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	        
	        dao.reviewFileDelete(reviewId);
	        dao.reviewReadCountDelete(reviewId);
	        dao.reviewDelete(reviewId);        
	        reviewResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        reviewResultDto.setResult(FAIL);
	    }
	    
	    return reviewResultDto;
	}
	
	@Override
	public ReviewResultDto reviewDetail(ReviewParamDto reviewParamDto) {
	    
	    ReviewResultDto reviewResultDto = new ReviewResultDto();
	    try {
	        int userReadCnt = dao.reviewUserReadCount(reviewParamDto);
	        if( userReadCnt == 0 ) {
	            dao.reviewUserReadInsert(reviewParamDto.getReviewId(), reviewParamDto.getUserSeq());
	            dao.reviewReadCountUpdate(reviewParamDto.getReviewId());
	        }
	        
	        ReviewDto reviewDto = dao.reviewDetail(reviewParamDto);
	        if( reviewDto.getUserSeq() == reviewParamDto.getUserSeq() ) {
	            reviewDto.setSameUser(true);
	        }else {
	            reviewDto.setSameUser(false);
	        }
	        
	        // 좋아요 했는지 안했는지 체크
//	        boolean check = goodDao.goodSelect(reviewParamDto);
	        
	        List<ReviewFileDto> fileList = dao.reviewDetailFileList(reviewDto.getReviewId());
	
	        reviewDto.setFileList(fileList);
	        reviewResultDto.setDto(reviewDto);
//	        reviewResultDto.setCheck(check);
	        reviewResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        reviewResultDto.setResult(FAIL);
	    }
	    System.out.println(reviewResultDto);
	    return reviewResultDto;
	}
	
	@Override
	public ReviewResultDto reviewList(ReviewParamDto reviewParamDto) {
	    System.out.println("service ReviewResultDto : " + reviewParamDto);
	    ReviewResultDto reviewResultDto = new ReviewResultDto();
	    
	    try {
	    	List<ReviewDto> list = dao.reviewList(reviewParamDto);
	        int count = dao.reviewListTotalCount();            
	        reviewResultDto.setList(list);
	        reviewResultDto.setCount(count);
	        reviewResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        reviewResultDto.setResult(FAIL);
	    }
	    System.out.println("reviewResultDto : " + reviewResultDto);
	    return reviewResultDto;
	}
	
	@Override
	public ReviewResultDto reviewListSearchWord(ReviewParamDto reviewParamDto) {
	    
	    ReviewResultDto reviewResultDto = new ReviewResultDto();
	    
	    try {
	        List<ReviewDto> list = dao.reviewListSearchWord(reviewParamDto);
	        int count = dao.reviewListSearchWordTotalCount(reviewParamDto);
	        
	        reviewResultDto.setList(list);
	        reviewResultDto.setCount(count);
	        
	        reviewResultDto.setResult(SUCCESS);
	    
	    }catch(Exception e) {
	        e.printStackTrace();
	        reviewResultDto.setResult(FAIL);
	    }
	    
	    return reviewResultDto;
	}
}
