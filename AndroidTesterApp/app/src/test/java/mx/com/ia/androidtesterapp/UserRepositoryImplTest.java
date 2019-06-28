package mx.com.ia.androidtesterapp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Random;

import mx.com.ia.androidtesterapp.Implementations.UserRepositoryImpl;
import mx.com.ia.androidtesterapp.Interfaces.UserApi;
import mx.com.ia.androidtesterapp.Interfaces.UserDao;
import mx.com.ia.androidtesterapp.Interfaces.UserRepository;
import mx.com.ia.androidtesterapp.Objects.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserRepositoryImplTest {

    //Test subject
    UserRepository userRepository;

    //Collaborators
    UserApi userApi;
    UserDao userDao;

    //Utilities
    User userFromApi;
    User userFromDao;

    @Before
    public void setUp() {
        userApi = mock(UserApi.class);
        userFromApi = new User(0, "fromApi");
        when(userApi.getUser(anyInt())).thenReturn(userFromApi);

        userDao = mock(UserDao.class);
        userFromDao = new User(1, "fromDao");
        when(userDao.getUser(anyInt())).thenReturn(userFromDao);

        userRepository = new UserRepositoryImpl(userDao, userApi);
    }

    @Test
    public void repositoryAsksForUserToDaoWithProperUserId() {
        int userId = new Random().nextInt((100 - 1) + 1) + 1;

        //Ejecuta el metodo getUser de UserRepositoryImpl
        userRepository.getUser(userId);

        //verifica que el metodo getUser del DAO sea ejecutado solamente 1 vez
        verify(userDao, times(1)).getUser(userId);
    }

    @Test
    public void ifDaoReturnsUserThenApiIsNotCalled() {
        //Ejecuta el metodo getUser de UserRepositoryImpl
        userRepository.getUser(0);

        //Verifica que nunca sea invocado el metodo getUser de la implementacion de UserApi, ya que el DAO encontro el usuario en un principio
        verify(userApi, never()).getUser(anyInt());
    }

    @Test
    public void ifDaoDontReturnsUserThenApiIsCalled() {
        //Ejecuta el metodo getUser de UserRepositoryImpl
        userRepository.getUser(0);


        //para que funcione en el setup hay que quitar el when del llamado a la funcion o regresar un null
        verify(userApi, atLeastOnce()).getUser(anyInt());
    }

    @Test
    public void ifDaoDoesNotReturnUserThenRepositoryAsksToApi() {
        //regresará un nulo cuando se haga el llamado a la funcion getUser del DAO
        when(userDao.getUser(anyInt())).thenReturn(null);

        //Ejecuta el metodo getUser de UserRepositoryImpl
        userRepository.getUser(0);

        //Verifica que sea invocado una vez el metodo getUser de DAO
        verify(userDao, times(1)).getUser(anyInt());

        //Verifica que sea invocado una vez el metodo getUser de API, ya que el DAO NO encontro el usuario.
        verify(userApi, times(1)).getUser(anyInt());
    }

    @Test
    public void ifDaoReturnsUserThenRepositoryReturnsSameUser() {
        //obtiene el usuario
        User user = userRepository.getUser(0);

        //verifica que ambos objetos sean los mismos
        assertEquals(user, userFromDao);

        //verifica el objeto obtenido tengan el id: 1
        assertEquals(user.getId(), 1);

        //verifica el objeto obtenido tengan el nombre: fromDao
        assertEquals(user.getUserName(), "fromDao");

        //otras verificaciones
        assertNotNull(user);
        assertTrue(user.getId() == 1);
        assertFalse(user.getId() == 2);
        assertNotEquals(user.getId(), 2);
        assertNotEquals(user, userFromApi);
    }

    @Test
    public void UserIsAskedToDaoBeforeAskingToApi() {
        //regresará un nulo cuando se haga el llamado a la funcion getUser del DAO
        when(userDao.getUser(anyInt())).thenReturn(null);

        //Ejecuta el metodo getUser de UserRepositoryImpl
        userRepository.getUser(0);

        //verifica que se hayan invocado las funciones getUser de DAO y API, en el orden correcto.
        InOrder orderVerifier = inOrder(userDao, userApi);//se pasan lo mock como parametros

        //la verificación se invoca a través de la función "verify" del objeto InOrder. Se realizan verificaciones en el orden correcto o deseado
        orderVerifier.verify(userDao, times(1)).getUser(anyInt()); //Ademas verifica que sea invocado una vez el metodo
        orderVerifier.verify(userApi).getUser(anyInt());
    }

    @Test
    public void IfUserIsRecoveredFromApiThenThatUserIsStoredThroughDao() {
        //regresará un nulo cuando se haga el llamado a la funcion getUser del DAO
        when(userDao.getUser(anyInt())).thenReturn(null);

        //Ejecuta el metodo getUser de UserRepositoryImpl
        userRepository.getUser(0);

        //se define un objeto Captor con el tipo de objeto (User) que vamos a capturar
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //Verifica que sea invocado una vez el metodo getUser de API
        verify(userApi, times(1)).getUser(anyInt());

        //Verifica que sea invocado una vez el metodo storeUser de DAO y se Captura el objeto intercambiado
        verify(userDao, times(1)).storeUser(captor.capture());

        //verificando que el usuario recuperado desde el API es almacenado a través del DAO, es decir que sean el mismo objeto
        assertEquals(captor.getValue(), userFromApi);

        //verifica el objeto capturado tengan el mismo nombre del objeto userFromApi
        assertEquals(captor.getValue().getUserName(), userFromApi.getUserName());
        //verifica el objeto capturado tengan el mismo id del objeto userFromApi
        assertEquals(captor.getValue().getId(), userFromApi.getId());
    }

    /*
        PROBANDO ERRORES
     */

    @Test(expected = IllegalStateException.class) //Se espera que suceda este tipo de error
    public void whenDaoFailsRecoveringUserAnIllegalStateExceptionIsThrown() {
        //Se configura para que el DAO al fallar lance una IllegalStateExceptión
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new IllegalStateException();
            }
        };
        doAnswer(answer).when(userDao).getUser(anyInt());

        //Ejecuta el metodo getUser de UserRepositoryImpl
        userRepository.getUser(0);
    }

    @Test
    public void whenDaoFailsRecoveringUserTheExceptionIsPropagatedAsIs() {
        IllegalStateException exception = new IllegalStateException();

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new Exception();
            }
        };
        doAnswer(answer).when(userDao).getUser(anyInt());

        try {
            userRepository.getUser(0);
            fail();
        } catch (Exception e) {
            assertEquals(e, exception);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void whenApiFailsRecoveringUserAnIllegalStateExceptionIsThrown() {
        when(userDao.getUser(anyInt())).thenReturn(null);

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new IllegalStateException();
            }
        };
        doAnswer(answer).when(userApi).getUser(anyInt());
        userRepository.getUser(0);
    }

    @Test
    public void whenApiFailsRecoveringUserTheExceptionIsPropagatedAsIs() {
        when(userDao.getUser(anyInt())).thenReturn(null);
        IllegalStateException exception = new IllegalStateException();

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new Exception();
            }
        };
        doAnswer(answer).when(userApi).getUser(anyInt());

        try {
            userRepository.getUser(0);
            fail();
        } catch (Exception e) {
            assertEquals(e, exception);
        }
    }

    @Test
    public void whenDaoFailsStoringUserTheExceptionIsWrappedProperly() {
        when(userDao.getUser(anyInt())).thenReturn(null);
        IllegalStateException exception = new IllegalStateException();

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new Exception();
            }
        };
        doAnswer(answer).when(userDao).storeUser(any(User.class));

        try {
            userRepository.getUser(0);
            fail();
        } catch (Exception e) {
            assert (e instanceof IllegalArgumentException);
            assertEquals(e.getCause(), exception);
            assertEquals(e.getMessage(), "Storing failed!");
        }
    }
}
