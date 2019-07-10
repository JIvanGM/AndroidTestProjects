package mx.com.ia.androidtesterapp;

import org.junit.Before;
import org.junit.Test;

import mx.com.ia.androidtesterapp.Implementations.GetUserUseCaseImpl;
import mx.com.ia.androidtesterapp.Implementations.UserDetailPresenterImpl;
import mx.com.ia.androidtesterapp.Implementations.UserRepositoryImpl;
import mx.com.ia.androidtesterapp.Interfaces.GetUserUseCase;
import mx.com.ia.androidtesterapp.Interfaces.UserApi;
import mx.com.ia.androidtesterapp.Interfaces.UserDao;
import mx.com.ia.androidtesterapp.Interfaces.UserDetailPresenter;
import mx.com.ia.androidtesterapp.Interfaces.UserRepository;
import mx.com.ia.androidtesterapp.Objects.User;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;


class RecoveringUserIntegrationTest {

    //Test subject
    UserDetailPresenter userDetailPresenter;

    //Collaborators (no mocks)
    GetUserUseCase getUserUseCase;
    UserRepository userRepository;

    //Collaborators (mocks)
    UserDetailView userDetailView;
    UserApi userApi;
    UserDao userDao;

    @Before
    private void setUp() {
        //Init Mocks
        userDetailView = mock(UserDetailView.class);
        userApi = mock(UserApi.class);
        userDao = mock(UserDao.class);

        //Collaborators initialization (no mocks)
        userRepository = new UserRepositoryImpl(userDao, userApi);
        getUserUseCase = new GetUserUseCaseImpl(userRepository);

        //Test subject initialization
        userDetailPresenter = new UserDetailPresenterImpl(userDetailView, getUserUseCase);
    }

    @Test
    void whenDaoReturnsUserItIsPropagatedToTheViewAsIs(){
        User testUser = new User(2, "integrationTest");
        whenever(userDao.getUser(anyInt())).thenReturn(testUser);

        userDetailPresenter.onClick()

        val captor : KArgumentCaptor&amp;amp;amp;amp;amp;amp;amp;amp;amp;lt;User&amp;amp;amp;amp;amp;amp;amp;amp;amp;gt; = argumentCaptor()
        verify(userDetailView, times(1)).doSomethingWithUser(captor.capture())
        assertEquals(captor.firstValue, testUser)
        //Validate ID/Name if wanted
    }

}