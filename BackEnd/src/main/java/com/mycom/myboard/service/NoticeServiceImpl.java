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

import com.mycom.myboard.dao.NoticeDao;
import com.mycom.myboard.dto.NoticeDto;
import com.mycom.myboard.dto.NoticeFileDto;
import com.mycom.myboard.dto.NoticeParamDto;
import com.mycom.myboard.dto.NoticeResultDto;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	NoticeDao dao;
	
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
    public NoticeResultDto noticeInsert(NoticeDto dto, MultipartHttpServletRequest request) {
    	System.out.println("인설트 들어옴");
        NoticeResultDto noticeResultDto = new NoticeResultDto();
        try {
            dao.noticeInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"

            List<MultipartFile> fileList = request.getFiles("file");

            File uploadDir = new File(uploadPath + File.separator + uploadFolder);
            if (!uploadDir.exists()) uploadDir.mkdir();

	        for (MultipartFile part : fileList) {
	
	            int noticeId = dto.getNoticeId();
	            
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
	            NoticeFileDto noticeFileDto = new NoticeFileDto();
	            noticeFileDto.setNoticeId(noticeId);
	            noticeFileDto.setFileName(fileName);
	            noticeFileDto.setFileSize(part.getSize());
	            noticeFileDto.setFileContentType(part.getContentType());
	            String freeFileUrl = uploadFolder + "/" + savingFileName;
	            noticeFileDto.setFileUrl(freeFileUrl);
	            System.out.println("들어온곳: " + noticeFileDto);
	            dao.noticeFileInsert(noticeFileDto);
	        }
	
	        noticeResultDto.setResult(SUCCESS);
	    }catch(IOException e) {
	        e.printStackTrace();
	        noticeResultDto.setResult(FAIL);
	    }
	    return noticeResultDto;
	}
	
	@Override
	@Transactional
	public NoticeResultDto noticeUpdate(NoticeDto dto, MultipartHttpServletRequest request){
	    
	    NoticeResultDto noticeResultDto = new NoticeResultDto();
	
	    
	    try {
	        dao.noticeUpdate(dto);
	
	        List<MultipartFile> fileList = request.getFiles("file");
	        
	        File uploadDir = new File(uploadPath + File.separator + uploadFolder);
	        if (!uploadDir.exists()) uploadDir.mkdir();
	        
	        // 물리 파일 삭제, 첨부파일 여러개 고려
	        List<String> fileUrlList = dao.noticeFileUrlDeleteList(dto.getNoticeId());    
	        for(String fileUrl : fileUrlList) {    
	            File file = new File(uploadPath + File.separator, fileUrl);
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	
//	        dao.freeFileDelete(dto.getFreeId()); // 테이블 파일 삭제
	        
	        
	        for (MultipartFile part : fileList) {
	            int noticeId = dto.getNoticeId();
	            
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
	            NoticeFileDto noticeFileDto = new NoticeFileDto();
	            noticeFileDto.setNoticeId(noticeId);
	            noticeFileDto.setFileName(fileName);
	            noticeFileDto.setFileSize(part.getSize());
	            noticeFileDto.setFileContentType(part.getContentType());
	            String freeFileUrl = uploadFolder + "/" + savingFileName;
	            noticeFileDto.setFileUrl(freeFileUrl);
	            System.out.println("updateservice: " + noticeFileDto);
	            dao.noticeFileInsert(noticeFileDto);
	        }
	
	        noticeResultDto.setResult(SUCCESS);
	        
	    }catch(IOException e) {
	        e.printStackTrace();
	        noticeResultDto.setResult(FAIL);
	    }
	    
	    return noticeResultDto;
	}
	
	@Override
	@Transactional
	public NoticeResultDto noticeDelete(int noticeId) {
	    
	    NoticeResultDto noticeResultDto = new NoticeResultDto();
	    
	    try {
	        List<String> fileUrlList = dao.noticeFileUrlDeleteList(noticeId);
	        for(String fileUrl : fileUrlList) {
	            File file = new File(uploadPath + File.separator, fileUrl);                
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	        
	        dao.noticeFileDelete(noticeId);
	        dao.noticeReadCountDelete(noticeId);
	        dao.noticeDelete(noticeId);        
	        noticeResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        noticeResultDto.setResult(FAIL);
	    }
	    
	    return noticeResultDto;
	}
	
	@Override
	public NoticeResultDto noticeDetail(NoticeParamDto noticeParamDto) {
	    
	    NoticeResultDto noticeResultDto = new NoticeResultDto();
	    try {
	        int userReadCnt = dao.noticeUserReadCount(noticeParamDto);
	        if( userReadCnt == 0 ) {
	            dao.noticeUserReadInsert(noticeParamDto.getNoticeId(), noticeParamDto.getUserSeq());
	            dao.noticeReadCountUpdate(noticeParamDto.getNoticeId());
	        }
	        
	        NoticeDto noticeDto = dao.noticeDetail(noticeParamDto);
	        if( noticeDto.getUserSeq() == noticeParamDto.getUserSeq() ) {
	            noticeDto.setSameUser(true);
	        }else {
	            noticeDto.setSameUser(false);
	        }
	        
	        // 좋아요 했는지 안했는지 체크
//	        boolean check = goodDao.goodSelect(noticeParamDto);
	        
	        List<NoticeFileDto> fileList = dao.noticeDetailFileList(noticeDto.getNoticeId());
	
	        noticeDto.setFileList(fileList);
	        noticeResultDto.setDto(noticeDto);
//	        noticeResultDto.setCheck(check);
	        noticeResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        noticeResultDto.setResult(FAIL);
	    }
	    System.out.println(noticeResultDto);
	    return noticeResultDto;
	}
	
	@Override
	public NoticeResultDto noticeList(NoticeParamDto noticeParamDto) {
	    NoticeResultDto noticeResultDto = new NoticeResultDto();
	    
	    try {
	    	List<NoticeDto> list = dao.noticeList(noticeParamDto);
	        int count = dao.noticeListTotalCount();            
	        noticeResultDto.setList(list);
	        noticeResultDto.setCount(count);
	        noticeResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        noticeResultDto.setResult(FAIL);
	    }
	    System.out.println("noticeResultDto : " + noticeResultDto);
	    return noticeResultDto;
	}
	
	@Override
	public NoticeResultDto noticeListSearchWord(NoticeParamDto noticeParamDto) {
	    
	    NoticeResultDto noticeResultDto = new NoticeResultDto();
	    
	    try {
	        List<NoticeDto> list = dao.noticeListSearchWord(noticeParamDto);
	        int count = dao.noticeListSearchWordTotalCount(noticeParamDto);
	        
	        noticeResultDto.setList(list);
	        noticeResultDto.setCount(count);
	        
	        noticeResultDto.setResult(SUCCESS);
	    
	    }catch(Exception e) {
	        e.printStackTrace();
	        noticeResultDto.setResult(FAIL);
	    }
	    
	    return noticeResultDto;
	}
}
