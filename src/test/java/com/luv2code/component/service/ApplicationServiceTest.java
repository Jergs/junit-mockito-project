package com.luv2code.component.service;

import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ApplicationServiceTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CollegeStudent student;
    @Autowired
    private StudentGrades studentGrades;
    //@Mock
    @MockBean
    private ApplicationDao applicationDao;

    //@InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        student.setFirstname("Egor");
        student.setLastname("Gavriliuk");
        student.setEmailAddress("myemail@gmail.com");
        student.setStudentGrades(studentGrades);
    }

    @Test
    public void assertEqualTestGrades() {
        when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults()))
                .thenReturn(100.0);

        assertEquals(100, applicationService.addGradeResultsForSingleClass(
                student.getStudentGrades().getMathGradeResults()));

        verify(applicationDao, times(1))
                .addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults());
    }

    @Test
    public void testAssertNotNull() {
        when(applicationDao.checkNull(any())).thenReturn(true);
        assertNotNull(applicationService.checkNull(student.getStudentGrades().getMathGradeResults()));
    }

    @Test
    public void throwRuntimeError() {
        CollegeStudent student1 = applicationContext.getBean("collegeStudent", CollegeStudent.class);
        when(applicationDao.checkNull(student1)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(student1));
    }

    @Test
    public void throwRuntimeErrorSecondApproach() {
        CollegeStudent student1 = applicationContext.getBean("collegeStudent", CollegeStudent.class);
        doThrow(new RuntimeException()).when(applicationDao.checkNull(student1));

        assertThrows(RuntimeException.class, () -> applicationService.checkNull(student1));
    }
}
