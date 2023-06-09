package mx.finerio.synchronizer.aop

import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.springframework.stereotype.Component

@Component
@Aspect
class CredentialServiceFindAllIdsByInstitutionId {

  final static Logger log = LoggerFactory.getLogger(
      'mx.finerio.synchronizer.aop.CredentialServiceFindAllIdsByInstitutionId' )

  @Pointcut(
    value='execution(java.util.List mx.finerio.synchronizer.services.CredentialService.findAllIdsByInstitutionId(..)) && bean(credentialService) && args(institutionId, lastUpdated)',
    argNames='institutionId, lastUpdated'
  )
  public void findAllIdsByInstitutionId( Long institutionId, Date lastUpdated ) {}

  @Before('findAllIdsByInstitutionId(institutionId, lastUpdated)')
  void before( Long institutionId, Date lastUpdated ) {
    log.info( "<< institutionId: {}, lastUpdated: {}", institutionId, lastUpdated )
  }

  @AfterReturning(
    pointcut='findAllIdsByInstitutionId(Long, java.util.Date)',
    returning='data'
  )
  void afterReturning( List data ) {
    log.info( ">> data: {} elements", data?.size() )
  }

  @AfterThrowing(
    pointcut='findAllIdsByInstitutionId(Long, java.util.Date)',
    throwing='e'
  )
  void afterThrowing( Exception e ) {
    log.info( "XX ${e.class.simpleName} - ${e.message}" )
  }

}
