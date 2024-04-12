package com.dawnbit.master.person;

import com.dawnbit.common.utils.ConstantUtils;
import com.dawnbit.common.utils.PagingSortingUtils;
import com.dawnbit.entity.master.*;
import com.dawnbit.master.Employee.EmployeeRepository;
import com.dawnbit.master.Labour.LabourRepository;
import com.dawnbit.master.OrganisationMaster.OrganisationRepository;
import com.dawnbit.master.OrganisationMaster.OrganisationService;
import com.dawnbit.master.Region.RegionRepository;
import com.dawnbit.master.country.CountryRepository;
import com.dawnbit.master.craftSkill.CraftSkillRepository;
import com.dawnbit.master.externalDTO.PersonDTO;
import com.dawnbit.master.externalDTO.PersonDropDownDTO;
import com.dawnbit.master.externalDTO.SearchModelDTO;
import com.dawnbit.master.externalDTO.dto.PersonAdditionalDataDTO;
import com.dawnbit.master.labourCraftSkill.LabourCraftSkillRepository;
import com.dawnbit.master.userdetails.UserDetailsRepo;
import com.dawnbit.master.userlogin.UserLoginRepo;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    UserLoginRepo userRepository;
    @Autowired
    UserDetailsRepo userDetailsRepo;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private LabourRepository labourRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

//    @Autowired
//    private CraftRepository craftRepository;
//
//    @Autowired
//    private SkillRepository skillRepository;

    @Autowired
    private LabourCraftSkillRepository labourCraftSkillRepository;

    @Autowired
    private CraftSkillRepository craftSkillRepository;
    @Autowired
    private OrganisationService organisationService;


    public static Page<?> getObjects(int limit, int offset, Map<String, Object> resultmap) {
        Pageable pageable;
        final List<?> searchedLists = (List<?>) resultmap.get(ConstantUtils.DATA_LIST);
        final long totalRows = (long) resultmap.get(ConstantUtils.TOTAL_ROWS);
        pageable = PageRequest.of(offset, limit);
        return new PageImpl<>(searchedLists, pageable, totalRows);
    }

    /**
     * @param personDTO
     * @return
     * @author Suraj
     * <p>
     * This method used to save person data
     */
    @Override
    public PersonDTO addUpdatePerson(PersonDTO personDTO) {
        Person person = new Person();
        //check person already exists or not
        //if exists then it will go in if otherwise in else part
        if (personDTO.getPersonId() != 0) {
            //update the Person
            Long personId = personDTO.getPersonId();
            Optional<Person> person1 = this.personRepository.findById(personId);
            if (person1.isPresent()) person = person1.get();
            BeanUtils.copyProperties(personDTO, person, "id");
//            if (personDTO.getIsEmployee() != null) {
//                person.setIsEmployee(personDTO.getIsEmployee());
//            }
//            if (personDTO.isStatus()) {
//                person.setStatus(ConstantUtils.ACTIVE);
//            } else {
//                person.setStatus(ConstantUtils.INACTIVE);
//            }
            personSetup(personDTO, person);
            //this condition is to check that update person record have value in isLabour field,
            // if value exists then create records in respective table(Labour)
            if (personDTO.getIsLabour()) {
                this.updateLabourData(personDTO, person);
            }
            //this condition is to check that update person record have value in isEmployee field,
            // if value exists then create records in respective table(Employee)
            if (personDTO.getIsEmployee()) {
                this.updateEmployeeData(personDTO, person);
            }
            return personDTO;

        } else {
//            method to create new Person
            //this.saveNewPersonRecords(personDTO,person)
            BeanUtils.copyProperties(personDTO, person, "id", "hireDate", "terminationDate", "jobTitle",
                    "designation", "higherQualification", "employeeType", "professionalQualification");
//            if (personDTO.isStatus()) {
//                person.setStatus(ConstantUtils.ACTIVE);
//            } else {
//                person.setStatus(ConstantUtils.INACTIVE);
//            }
            personSetup(personDTO, person);
            //after save of person create labour if isLabour value is true
            if (personDTO.getIsLabour()) {
                this.createLabourByPerson(personDTO, person);
            }
            //after save of person create employee  if  isEmployee value is true
            if (personDTO.getIsEmployee()) {
                this.createEmployeeByPerson(personDTO, person);
            }
        }
        return personDTO;
    }

    /**
     * @param personDTO
     * @param person
     * @author Suraj
     * <p>
     * This method used to add or update record in Labour Table on Person's Update
     */
    public void updateLabourData(PersonDTO personDTO, Person person) {
        Labour labour = null;
        /**
         * we have to check that ,we have to update or create Labour record because there is a case like user create
         * person and save it after that in update of person record user can create Labour.
         *
         * So , new labour create then it's labour id is 0
         */
        if (personDTO.getLabourId() == 0) {
            //this method create new record of Labour
            this.createLabourByPerson(personDTO, person);
        } else {
            //this is for update the record of person
            Optional<OrganisationMaster> orgMaster = Optional.empty();
            if (personDTO.getOrganisationId() != null) {
                orgMaster = this.organisationRepository.findById(Long.parseLong(personDTO.getOrganisationId()));
            }
            Optional<Labour> labour1 = this.labourRepository.findById(personDTO.getLabourId());
            if (labour1.isPresent()) labour = labour1.get();
            assert labour != null;
            BeanUtils.copyProperties(personDTO, labour, "id");
            labour.setPerson(person);
            assert Objects.requireNonNull(orgMaster).isPresent();
            orgMaster.ifPresent(labour::setOrganisationMaster);
            labour.setStatus("ACTIVE");

            this.labourRepository.save(labour);

            //after the labour record save then put data in LabourCraftSkill Table
            if (!personDTO.getAdditionalData().isEmpty()) {
                this.dataInLabourCraftSkill(personDTO, person, labour);
            }
        }

    }

    private void personSetup(PersonDTO personDTO, Person person) {
        String countryId = personDTO.getCountryId();
        String regionId = personDTO.getRegionId();
        if (countryId != null && !countryId.isEmpty()) {
            Optional<Country> country = this.countryRepository.findById(Long.valueOf(countryId));
            country.ifPresent(person::setCountry);
        }
        if (regionId != null && !regionId.isEmpty()) {
            Optional<Region> region = this.regionRepository.findById(Long.valueOf(regionId));
            region.ifPresent(person::setRegion);
        }
        this.personRepository.save(person);
    }

    /**
     * @param personDTO
     * @param person    this method is used to create Labour record from person when isLabour checkbox is checked from Person ui
     */
    private void createLabourByPerson(PersonDTO personDTO, Person person) {
        Optional<OrganisationMaster> orgMaster = Optional.empty();
        if (personDTO.getOrganisationId() != null) {
            orgMaster = this.organisationRepository.findById(Long.parseLong(personDTO.getOrganisationId()));
        }
        Labour labour = new Labour();
        BeanUtils.copyProperties(personDTO, labour, "id");
        labour.setPerson(person);
        assert Objects.requireNonNull(orgMaster).isPresent();
        orgMaster.ifPresent(labour::setOrganisationMaster);
        labour.setStatus("ACTIVE");

        this.labourRepository.save(labour);

        //after the labour record save then put data in LabourCraftSkill Table
        if (!personDTO.getAdditionalData().isEmpty()) {
            this.dataInLabourCraftSkill(personDTO, person, labour);
        }
    }

    /**
     * @param personDTO
     * @param person
     * @param labour
     * @author Suraj
     * <p>
     * This method is used to create records in LabourCraftSkill Table
     * this method is call when user create person and from that person ,he will create Labour and associate
     * craft and skills in Person UI
     */
    public void dataInLabourCraftSkill(PersonDTO personDTO, Person person, Labour labour) {
        List<PersonAdditionalDataDTO> additionalData = personDTO.getAdditionalData();
        for (PersonAdditionalDataDTO additionalDatum : additionalData) {
            if (additionalDatum.getCraftId() != 0 && additionalDatum.getSkillId() != 0) {
                LabourCraftSkill lcs = new LabourCraftSkill();
                CraftSkill cs = this.craftSkillRepository.getCraftSkillData(additionalDatum.getCraftId(), additionalDatum.getSkillId());
                lcs.setCraftSkill(cs);
                lcs.setLabour(labour);
                lcs.setOrganisationMaster(labour.getOrganisationMaster());
                this.labourCraftSkillRepository.save(lcs);
            }
        }
    }

    /**
     * @param personDTO
     * @param person
     * @author Suraj
     * <p>
     * This method is used create or update records of employee Table on person's update
     */
    private void updateEmployeeData(PersonDTO personDTO, Person person) {
        /*
         * We need to check whether we have to update an existing Employee record or create a new one. This is because there might be a scenario where a user creates a Person record and saves it. Later, while updating the Person's record, the user might decide to create an Employee.
         * <p>
         * Therefore, if a new Employee is being created, its Employee id will be 0.
         */
        if (personDTO.getEmployeeId() == 0) {
            //this method is used to create Employee Record
            this.createEmployeeByPerson(personDTO, person);
        } else {
            //this is used to update Employee Record
            Employee employee = null;
            Optional<OrganisationMaster> orgMaster = Optional.empty();
            if (personDTO.getOrganisationId() != null) {
                orgMaster = this.organisationRepository.findById(Long.parseLong(personDTO.getOrganisationId()));
            }
            Optional<Employee> employee1 = employeeRepository.findById(personDTO.getEmployeeId());
            if (employee1.isPresent()) employee = employee1.get();
            assert employee != null;
            BeanUtils.copyProperties(personDTO, employee, "employeeId");
            employee.setPerson(person);
            assert Objects.requireNonNull(orgMaster).isPresent();
            orgMaster.ifPresent(employee::setOrganisationMaster);
            employeeRepository.save(employee);
            UserDetails userDetails = userDetailsRepo.getUserDetailsByEmployeeId(employee.getEmployeeId());
            System.out.println("employee id is : " + userDetails);
            if (userDetails != null) {
                userDetails.setEmailId(personDTO.getEmail());
                userDetailsRepo.save(userDetails);
            }
//            if (employee.getUserDetails() != null) {
//                UserDetails userDetails = employee.getUserDetails();
//                userDetails.setEmailId(personDTO.getEmail());
//                userDetailsRepo.save(userDetails);
//            }
        }
    }

    /**
     * @param personDTO
     * @param person
     * @author Suraj
     * <p>
     * This method is used to create Records in Employee Table
     */
    private void createEmployeeByPerson(PersonDTO personDTO, Person person) {
        Optional<OrganisationMaster> orgMaster = Optional.empty();
        if (personDTO.getOrganisationId() != null) {
            orgMaster = this.organisationRepository.findById(Long.parseLong(personDTO.getOrganisationId()));
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(personDTO, employee, "employeeId");
        employee.setPerson(person);
        employee.setStatus("ACTIVE");
        assert Objects.requireNonNull(orgMaster).isPresent();
        orgMaster.ifPresent(employee::setOrganisationMaster);
        this.employeeRepository.save(employee);
    }

    @Override
    public Boolean deletePersonById(String personId) {
        if (personId != null) {
            this.personRepository.deleteById(Long.valueOf(personId));
            return true;
        }
        return false;
    }

    @Override
    public Page<?> fetchPersonData(SearchModelDTO searchModel) {
        List<String> orgIds = this.organisationService.getOrganizationIdsByUserRole();
//        System.out.println("organistaion list is : " + orgIds);
        //		final SearchModelDTO searchModel =null;
        final int limit = searchModel.getLimit();
        final int offset = searchModel.getOffset();
        final String sortField = searchModel.getSortingField();
        final String sortDirection = searchModel.getSortDirection();
//        List<String> organizationIds = new ArrayList<>();
//        organizationIds = organisationService.getOrganizationIdsByUserRole();

        try (Session session = this.entityManager.unwrap(Session.class)) {
            if (sortField == null) {
                searchModel.setSortingField("prs.firstName");
            }
            if (sortDirection == null || sortDirection.isEmpty()) {
                searchModel.setSortDirection("desc");
            }
            final Map<String, Object> params = new HashMap<>();
//   String hqlQuery = "SELECT new com.dawnbit.master.externalDTO.SearchModelDTO(prs, l, e, org) " +
//                    "FROM Person prs " +
//                    "LEFT JOIN Labour l ON prs.id = l.person.id " +
//                    "LEFT JOIN Employee e ON prs.id = e.person.id " +
//                    "LEFT JOIN OrganisationMaster org ON " +
//                    "(l.organisationMaster.id = org.id OR e.organisationMaster.id = org.id) " +
//                    "WHERE prs.firstName IS NOT NULL " +
//                    "AND (prs.isLabour = true OR prs.isEmployee = true) " +
//                    "AND (prs.isLabour = true OR prs.isEmployee = true)";
            String hqlQuery = "SELECT new com.dawnbit.master.externalDTO.SearchModelDTO(prs, l, e, org) " +
                    "FROM Person prs " +
                    "LEFT JOIN Labour l ON prs.id = l.person.id " +
                    "LEFT JOIN Employee e ON prs.id = e.person.id " +
                    "LEFT JOIN OrganisationMaster org ON " +
                    "(l.organisationMaster.id = org.id OR e.organisationMaster.id = org.id) " +
                    "WHERE prs.firstName IS NOT NULL " +
                    "AND (org.id IN :orgIds " +  // Start of organization ID condition
                    "OR org.id IS NULL)";        // Persons not belonging to any organization
            params.put("orgIds", orgIds);
//            params.put("excludeId", 1L);


//            params.put("organizationIds", organizationIds);
            final Map<String, Object> dataMap = PagingSortingUtils.fetchDataListWithUpdatedQueryStringInMaps(params,
                    hqlQuery, searchModel, session);
            final Map<String, Object> resultmap = PagingSortingUtils.addPaginationDataInDataMap(dataMap, params,
                    searchModel, session, "prs");


            /**
             * Fetch list
             */
            return getObjects(limit, offset, resultmap);
        }
    }

    @Override
    public PersonDTO getPersonDataById(String personId) {
        if (personId != null)
            return this.personRepository.getPeronDataById(Long.valueOf(personId));
        return null;
    }

    @Override
    public List<PersonDropDownDTO> getPersonList(String organisationId) {
        List<Long> personIds = this.labourRepository.getPersonIdsNotInLabour(organisationId);
        if (personIds.isEmpty()) {
            return this.personRepository.getPersonIdAndName();
        } else {
            return this.personRepository.getPersonIdAndName(personIds);
        }
    }

    @Override
    public List<PersonDropDownDTO> getPersonListAndCurrentPerson(String labourId, String organisationId) {
        List<PersonDropDownDTO> personDropDownDTOList = this.getPersonList(organisationId);
        Long personIds = this.labourRepository.getPersonIdAndCurrentperson(labourId);
        PersonDropDownDTO personDropDownDTO = this.personRepository.getPersonIdAndCurrentPersonWithName(personIds);
        personDropDownDTOList.add(personDropDownDTO);
        return personDropDownDTOList;
    }

    @Override
    public Object getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Object getPersonListThatAreEmployees() {
        return personRepository.findAllEmployees();
    }

    @Override
    public List<PersonAdditionalDataDTO> fetchLabourCraftSkillData(Long labourId) {
        return this.personRepository.fetchLabourCraftSkillData(labourId);
    }


    /**
     * @author Suraj
     *
     * This method used to fetch records from LabourCraftSkill Table
     *
     * @param labourId
     * @return
     */
//    @Override
//    public List<PersonAdditionalDataDTO> fetchLabourCraftSkillData(Long labourId){
//        return this.personRepository.fetchLabourCraftSkillData(labourId);
//    }


    /**
     * @param email
     * @return
     * @author Suraj
     * <p>
     * This method is used to check that email duplicacy for person
     */
    @Override
    public String checkEmailDuplicacy(String email) {
        String value = this.personRepository.checkEmailDuplicacy(email);
        if (value != null) {
            return "duplicateEmail";
        } else {
            return "noDuplicateEmail";
        }
    }

    /**
     * @param labourCraftSkillId
     * @return
     * @author Suraj
     * <p>
     * This method is used to delete records from LabourCraftSkillTable
     */
    @Override
    public String deleteCraftSkills(Long labourCraftSkillId) {
        long labourId = labourCraftSkillRepository.fetchLabourId(labourCraftSkillId);
        int countskill = labourCraftSkillRepository.countSkill(labourId);
        if (countskill > 1) {
            labourCraftSkillRepository.deleteById(labourCraftSkillId);
            return "successfully deleted";
        } else {
            return "atleast one skill must be associated";
        }
    }
}
