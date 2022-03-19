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

import com.mycom.myboard.dao.FreeDao;
import com.mycom.myboard.dao.GoodDao;
import com.mycom.myboard.dto.FreeDto;
import com.mycom.myboard.dto.FreeFileDto;
import com.mycom.myboard.dto.FreeParamDto;
import com.mycom.myboard.dto.FreeResultDto;

@Service
public class FreeServiceImpl implements FreeService{
	
	@Autowired
	FreeDao dao;
	
	@Autowired
	GoodDao goodDao;
	
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
    public FreeResultDto freeInsert(FreeDto dto, MultipartHttpServletRequest request) {
    	
        FreeResultDto freeResultDto = new FreeResultDto();
        try {
            dao.freeInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"

            List<MultipartFile> fileList = request.getFiles("file");

            File uploadDir = new File(uploadPath + File.separator + uploadFolder);
            if (!uploadDir.exists()) uploadDir.mkdir();

	        for (MultipartFile part : fileList) {
	
	            int freeId = dto.getFreeId();
	            
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
	            FreeFileDto freeFileDto = new FreeFileDto();
	            freeFileDto.setFreeId(freeId);
	            freeFileDto.setFileName(fileName);
	            freeFileDto.setFileSize(part.getSize());
	            freeFileDto.setFileContentType(part.getContentType());
	            String freeFileUrl = uploadFolder + "/" + savingFileName;
	            freeFileDto.setFileUrl(freeFileUrl);
	            System.out.println("들어온곳: " + freeFileDto);
	            dao.freeFileInsert(freeFileDto);
	        }
	
	        freeResultDto.setResult(SUCCESS);
	    }catch(IOException e) {
	        e.printStackTrace();
	        freeResultDto.setResult(FAIL);
	    }
	    return freeResultDto;
	}
	
	@Override
	@Transactional
	public FreeResultDto freeUpdate(FreeDto dto, MultipartHttpServletRequest request){
	    
	    FreeResultDto freeResultDto = new FreeResultDto();
	
	    
	    try {
	        dao.freeUpdate(dto);
	
	        List<MultipartFile> fileList = request.getFiles("file");
	        
	        File uploadDir = new File(uploadPath + File.separator + uploadFolder);
	        if (!uploadDir.exists()) uploadDir.mkdir();
	        
	        // 물리 파일 삭제, 첨부파일 여러개 고려
	        List<String> fileUrlList = dao.freeFileUrlDeleteList(dto.getFreeId());    
	        for(String fileUrl : fileUrlList) {    
	            File file = new File(uploadPath + File.separator, fileUrl);
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	
//	        dao.freeFileDelete(dto.getFreeId()); // 테이블 파일 삭제
	        
	        
	        for (MultipartFile part : fileList) {
	            int freeId = dto.getFreeId();
	            
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
	            FreeFileDto freeFileDto = new FreeFileDto();
	            freeFileDto.setFreeId(freeId);
	            freeFileDto.setFileName(fileName);
	            freeFileDto.setFileSize(part.getSize());
	            freeFileDto.setFileContentType(part.getContentType());
	            String freeFileUrl = uploadFolder + "/" + savingFileName;
	            freeFileDto.setFileUrl(freeFileUrl);
	            System.out.println("updateservice: " + freeFileDto);
	            dao.freeFileInsert(freeFileDto);
	        }
	
	        freeResultDto.setResult(SUCCESS);
	        
	    }catch(IOException e) {
	        e.printStackTrace();
	        freeResultDto.setResult(FAIL);
	    }
	    
	    return freeResultDto;
	}
	
	@Override
	@Transactional
	public FreeResultDto freeDelete(int freeId) {
	    
	    FreeResultDto freeResultDto = new FreeResultDto();
	    
	    try {
	        List<String> fileUrlList = dao.freeFileUrlDeleteList(freeId);
	        for(String fileUrl : fileUrlList) {
	            File file = new File(uploadPath + File.separator, fileUrl);                
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	        
	        dao.freeFileDelete(freeId);
	        dao.freeReadCountDelete(freeId);
	        dao.freeDelete(freeId);        
	        freeResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        freeResultDto.setResult(FAIL);
	    }
	    
	    return freeResultDto;
	}
	
	@Override
	public FreeResultDto freeDetail(FreeParamDto freeParamDto) {
	    
	    FreeResultDto freeResultDto = new FreeResultDto();
	    try {
	        int userReadCnt = dao.freeUserReadCount(freeParamDto);
	        if( userReadCnt == 0 ) {
	            dao.freeUserReadInsert(freeParamDto.getFreeId(), freeParamDto.getUserSeq());
	            dao.freeReadCountUpdate(freeParamDto.getFreeId());
	        }
	        
	        FreeDto freeDto = dao.freeDetail(freeParamDto);
	        if( freeDto.getUserSeq() == freeParamDto.getUserSeq() ) {
	            freeDto.setSameUser(true);
	        }else {
	            freeDto.setSameUser(false);
	        }
	        
	        // 좋아요 했는지 안했는지 체크
	        boolean check = goodDao.goodSelect(freeParamDto);
	        
	        List<FreeFileDto> fileList = dao.freeDetailFileList(freeDto.getFreeId());
	
	        freeDto.setFileList(fileList);
	        freeResultDto.setDto(freeDto);
	        freeResultDto.setCheck(check);
	        freeResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        freeResultDto.setResult(FAIL);
	    }
	    System.out.println(freeResultDto);
	    return freeResultDto;
	}
	
	@Override
	public FreeResultDto freeList(FreeParamDto freeParamDto) {
	    System.out.println("service FreeResultDto : " + freeParamDto);
	    FreeResultDto freeResultDto = new FreeResultDto();
	    
	    try {
	    	List<FreeDto> list = dao.freeList(freeParamDto);
	        int count = dao.freeListTotalCount();            
	        freeResultDto.setList(list);
	        freeResultDto.setCount(count);
	        freeResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        freeResultDto.setResult(FAIL);
	    }
	    System.out.println("freeResultDto : " + freeResultDto);
	    return freeResultDto;
	}
	
	@Override
	public FreeResultDto freeListSearchWord(FreeParamDto freeParamDto) {
	    
	    FreeResultDto freeResultDto = new FreeResultDto();
	    
	    try {
	        List<FreeDto> list = dao.freeListSearchWord(freeParamDto);
	        int count = dao.freeListSearchWordTotalCount(freeParamDto);
	        
	        freeResultDto.setList(list);
	        freeResultDto.setCount(count);
	        
	        freeResultDto.setResult(SUCCESS);
	    
	    }catch(Exception e) {
	        e.printStackTrace();
	        freeResultDto.setResult(FAIL);
	    }
	    
	    return freeResultDto;
	}
}
