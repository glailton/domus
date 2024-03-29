package glailton.io.github.domus.ui.presentation.screens.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.data.local.DataStoreManager
import glailton.io.github.domus.core.data.local.PreferenceDataStoreConstants
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import glailton.io.github.domus.utils.CoroutineRules
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRules()

    private val repository: FirebaseAuthRepository = mockk()
    private val dataStoreManager: DataStoreManager = mockk()
    private lateinit var vm: LoginViewModel

    @Before
    fun setup() {
        vm = LoginViewModel(repository, dataStoreManager)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should login successfully`() = runTest {
        val userMock: FirebaseUser = mockk()

        val email = "email"
        val password = "password"

        coEvery {
            repository.login(email, password)
        } returns FirebaseResult.Success(userMock)

        coEvery {
            dataStoreManager.putPreference(PreferenceDataStoreConstants.USER_KEY, "1")
        } returns Unit

        every {
            userMock.uid
        } returns "1"

        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.login()

        val state = vm.state.value

        state.isSuccessLogin shouldBe true
        state.email shouldBe email
        state.isLoading shouldBe false
    }

    @Test
    fun `should not login when email and password is empty`() = runTest {
        val userMock = mock(FirebaseUser::class.java)

        val email = ""
        val password = ""

        coEvery {
            repository.login(email, password)
        } returns FirebaseResult.Success(userMock)

        vm.updateInfo(email, password)
        vm.login()

        val state = vm.state.value

        state.isSuccessLogin shouldBe false
        state.loginError shouldBe true
        state.email shouldBe email
        state.isLoading shouldBe false
    }

    @Test
    fun `should not login when return error`() = runTest {
        val email = "email"
        val password = "password"

        coEvery {
            repository.login(email, password)
        } returns FirebaseResult.Error(
            FirebaseAuthException(
                "ERROR_INVALID_EMAIL",
                "Invalid Email"
            )
        )

        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.login()

        val state = vm.state.value

        state.isSuccessLogin shouldBe false
        state.loginError shouldBe true
        state.email shouldBe email
        state.isLoading shouldBe false
        state.loginErrorMessage shouldNotBe null
    }

    @Test
    fun `should hide dialog`() = runTest {
        vm.hideErrorDialog()

        val state = vm.state.value

        state.loginError shouldBe false
    }
}