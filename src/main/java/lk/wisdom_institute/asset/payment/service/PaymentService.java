package lk.wisdom_institute.asset.payment.service;



import lk.wisdom_institute.asset.batch_student.entity.BatchStudent;
import lk.wisdom_institute.asset.common_asset.model.enums.LiveDead;
import lk.wisdom_institute.asset.instalment_date.entity.InstalmentDate;
import lk.wisdom_institute.asset.payment.dao.PaymentDao;
import lk.wisdom_institute.asset.payment.entity.Payment;
import lk.wisdom_institute.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService implements AbstractService< Payment, Integer > {
  private final PaymentDao paymentDao;

  public PaymentService(PaymentDao paymentDao) {
    this.paymentDao = paymentDao;
  }

  public List< Payment > findAll() {
    return paymentDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());}

  public Payment findById(Integer id) {
    return paymentDao.getOne(id);
  }

  public Payment persist(Payment payment) {
    if ( payment.getId() == null )
      payment.setLiveDead(LiveDead.ACTIVE);
    return paymentDao.save(payment);
  }

  public boolean delete(Integer id) {
    Payment payment = paymentDao.getOne(id);
    payment.setLiveDead(LiveDead.STOP);
    paymentDao.save(payment);
    return false;
  }

  public List< Payment > search(Payment payment) {
    return null;
  }


  public Payment lastStudentOnDB() {
    return paymentDao.findFirstByOrderByIdDesc();
  }



  public List< Payment > findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {


    System.out.println(" astar "+startDateTime + "  end "+endDateTime+ "  size "+  paymentDao.findByCreatedAtIsBetween(startDateTime, endDateTime).size());
    return paymentDao.findByCreatedAtIsBetween(startDateTime, endDateTime);
  }

  public Payment findByInstalmentDateAndBatchStudent(InstalmentDate instalmentDate, BatchStudent batchStudent) {
  return paymentDao.findByInstalmentDateAndBatchStudent(instalmentDate, batchStudent);
  }
}
