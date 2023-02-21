package com.akashk.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.akashk.binding.CorrespondenceResponce;
import com.akashk.entity.CaseDetailsEntity;
import com.akashk.entity.CitizenEntity;
import com.akashk.entity.CorrespondenceEntity;
import com.akashk.entity.EligibilityDeterminationEntity;
import com.akashk.repository.CitizenRepository;
import com.akashk.repository.CorrespondceRepository;
import com.akashk.repository.EligibilityDeterminationRepository;
import com.akashk.util.MailUtil;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.ElementTags;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.DottedLineSeparator;

@Service
public class CorrespondenceServiceImpl implements CorrespondenceService {

	@Autowired
	private CorrespondceRepository correspondceRepo;
	@Autowired
	private EligibilityDeterminationRepository eligibilityRepo;
	@Autowired
	private CitizenRepository citizenRepo;
	@Autowired
	private MailUtil mailUtil;
	// private CaseDetailsEntity caseDetailsEntity = null;
	private CorrespondenceResponce correspondenceResponce = null;
	int totalRecords = 0;
	public int successRecords = 0;
	byte[] file = null;

	public int failRecords = 0;

	@Override
	
	public CorrespondenceResponce correspondenceProcess() {

		List<CorrespondenceEntity> corresponEntities = correspondceRepo.findByTriggerStatus("pending");
		totalRecords = corresponEntities.size();
		System.out.println(totalRecords);
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		for (CorrespondenceEntity corrspon : corresponEntities) {
			executorService.execute(() -> process(corrspon));

		//	 writeAndSendPdf(corrspon);
		}

		correspondenceResponce = new CorrespondenceResponce();
		correspondenceResponce.setTotalRecords(totalRecords);
		correspondenceResponce.setSuccessRecords(successRecords);
		correspondenceResponce.setFailRecords(failRecords);

		return correspondenceResponce;
	}

	@Transactional(readOnly = false)
	private void process(CorrespondenceEntity corrspon) {

		
		try {
			CaseDetailsEntity caseDetailsEntity = corrspon.getCaseDetailsEntity();
			EligibilityDeterminationEntity eligibilityEntity = eligibilityRepo
					.findBycaseId(caseDetailsEntity.getCaseId());
			Integer citizenId = caseDetailsEntity.getCitizen().getCitizenId();
			
			file = writePdf(caseDetailsEntity, eligibilityEntity,citizenId);

			/*
			 * String to = caseDetailsEntity.getCitizen().getEmailId(); String subject =
			 * " notice regrading RI Health applied plan"; String body =
			 * getMailBody("mailbody.txt", caseDetailsEntity.getCitizen().getFullName(),
			 * eligibilityEntity.getPlanStatus());
			 * 
			 * mailUtil.sendMail(to, subject, body, file);
			 */

		} catch (Exception e) {
			e.printStackTrace();
			++failRecords;
			return;
		}
		++successRecords;
		corrspon.setCorrespodencePdf(file);
		corrspon.setTriggerStatus("complete");
		correspondceRepo.save(corrspon);

	}

	private byte[] writePdf(CaseDetailsEntity caseDetails, EligibilityDeterminationEntity eligibilityEntity,Integer citizenId) {

		System.out.println(" in pdf");

		Document document = new Document(PageSize.A4);
		//CitizenEntity citizen = caseDetails.getCitizen();
		CitizenEntity citizen = citizenRepo.findById(citizenId).get();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);

		document.open();

		Paragraph notice = new Paragraph(

		eligibilityEntity.getPlanStatus().equals("Approve") ? "Approve Notice" : "Denied Notice");
		notice.setAlignment(ElementTags.ALIGN_CENTER);
		document.add(notice);
		Paragraph line = new Paragraph();
		DottedLineSeparator dottedLineSeparator = new DottedLineSeparator();

		dottedLineSeparator.setOffset(-2);
		dottedLineSeparator.setGap(0f);
		line.add(dottedLineSeparator);

		document.add(line);
		document.add(new Paragraph(Chunk.NEWLINE));

		document.add(new Paragraph("Case number : " + caseDetails.getCaseId()));
		document.add(new Paragraph(Chunk.NEWLINE));
		document.add(new Paragraph("name :" + citizen.getFullName()));
		document.add(new Paragraph(Chunk.NEWLINE));
		document.add(new Paragraph("SSN : XXX XX " + citizen.getSsnNo().substring(5)));
		document.add(new Paragraph(Chunk.NEWLINE));
		document.add(new Paragraph("plan name : " + caseDetails.getPlanName()));
		document.add(new Paragraph(Chunk.NEWLINE));
		document.add(new Paragraph("plan status : " + eligibilityEntity.getPlanStatus()));
		document.add(new Paragraph(Chunk.NEWLINE));

		if ("Approve".equalsIgnoreCase(eligibilityEntity.getPlanStatus())) {
			document.add(new Paragraph("plan start date : " + eligibilityEntity.getStartDate()));
			document.add(new Paragraph(Chunk.NEWLINE));
			document.add(new Paragraph("plan end date : " + eligibilityEntity.getEndDate()));
			document.add(new Paragraph(Chunk.NEWLINE));
			document.add(new Paragraph("plan benefit amount : " + eligibilityEntity.getBenefitAmount()));
			document.add(new Paragraph(Chunk.NEWLINE));
		} else {
			document.add(new Paragraph("denial reason : " + eligibilityEntity.getDenialReason()));
			document.add(new Paragraph(Chunk.NEWLINE));
		}

		document.add(line);
		document.add(new Paragraph(Chunk.NEWLINE));
		document.add(new Paragraph("DHS office address :  Reservoir Avenue. Providence, RI 02907, USA "));
		document.add(new Paragraph(Chunk.NEWLINE));
		document.add(new Paragraph("contact no : 1-401-721-6659 "));
		document.add(new Paragraph(Chunk.NEWLINE));
		document.add(new Paragraph("web site : https://dhs.ri.go "));
		document.close();
		pdfWriter.close();
		return byteArrayOutputStream.toByteArray();
	}

	private String getMailBody(String fileName, String name, String planStatus) {

		StringBuilder builder = new StringBuilder();
		try (Stream<String> fileLines = Files.lines(Paths.get(fileName))) {
			fileLines.forEach(line -> {
				line = line.replace("${user}", name);
				line = line.replace("${plan_status}", planStatus);
				builder.append(line);
			});
		} catch (IOException e) {

		}
		return builder.toString();
	}

}
