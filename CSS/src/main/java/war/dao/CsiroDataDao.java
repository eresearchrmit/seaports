package war.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import war.model.* ;

@Repository
public class CsiroDataDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private CsiroParamsDao csiroParamsDao;
	
	@Autowired
	private CsiroVariableDao csiroVariableDao;
	
	private List<CsiroData> csiroDataList;

	public CsiroData find(Integer id) {
		return entityManager.find(CsiroData.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<CsiroData> find(String regionName) throws NoResultException {
		Region region = regionDao.find(regionName);
		
		try {
			Query query = entityManager.createQuery("select d from CsiroData d where d.region = :region");
			query.setParameter("region", region);
			csiroDataList = query.getResultList();
			
			return csiroDataList;
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<CsiroData> find(String regionName, String emissionScenario, String climateModel, Integer assessmentYear) throws NoResultException {
		Region region = regionDao.find(regionName);
		CsiroParams parameters = csiroParamsDao.find(emissionScenario, climateModel, assessmentYear);
		
		try {
			Query query = entityManager.createQuery("select d from CsiroData d where d.region = :region and d.parameters = :parameters");
			query.setParameter("region", region);
			query.setParameter("parameters", parameters);
			csiroDataList = query.getResultList();
			
			return csiroDataList;
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	@Transactional
	public CsiroData find(String regionName, String emissionScenario, String climateModel, Integer assessmentYear, String variableName) throws NoResultException {
		Region region = regionDao.find(regionName);
		CsiroParams parameters = csiroParamsDao.find(emissionScenario, climateModel, assessmentYear);
		CsiroVariable variable = csiroVariableDao.find(variableName);
		
		try {
			Query query = entityManager.createQuery("select d from CsiroData d where d.region = :region and d.parameters = :parameters and d.variable = :variable");
			query.setParameter("region", region);
			query.setParameter("parameters", parameters);
			query.setParameter("variable", variable);
			
			return (CsiroData)(query.getSingleResult());
		}
		catch (NoResultException e) {
			throw new NoResultException(ERR_NO_RESULT);
		}
	}
	
	public static final String ERR_NO_RESULT = "No CSIRO data found corresponding to the specified parameters";
}
