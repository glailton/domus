package glailton.io.github.domus.ui.presentation.screens.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.data.local.DataStoreManager
import glailton.io.github.domus.core.data.local.PreferenceDataStoreConstants
import glailton.io.github.domus.firebase.FirebaseAuthRepository
import glailton.io.github.domus.utils.CoroutineRules
import io.kotest.matchers.shouldBe
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

@OptIn(ExperimentalCoroutinesApi::class)
class RegisterViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRules()

    private val repository: FirebaseAuthRepository = mockk()
    private val dataStoreManager: DataStoreManager = mockk()
    private lateinit var vm: RegisterViewModel

    @Before
    fun setup() {
        vm = RegisterViewModel(repository, dataStoreManager)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should signup successfully`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = "name"
        val email = "email"
        val password = "password"
        val phoneNumber = "112345698"
        val confirmPassword = "password"

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        coEvery {
            dataStoreManager.putPreference(PreferenceDataStoreConstants.USER_KEY, "1")
        } returns Unit

        every {
            userMock.uid
        } returns "1"

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe true
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not signup when fields are empty`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = ""
        val email = ""
        val password = ""
        val phoneNumber = ""
        val confirmPassword = ""

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        coEvery {
            dataStoreManager.putPreference(PreferenceDataStoreConstants.USER_KEY, "1")
        } returns Unit

        every {
            userMock.uid
        } returns "1"

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not signup when name field is empty`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = ""
        val email = "email"
        val password = "password"
        val phoneNumber = "112345698"
        val confirmPassword = "password"


        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        coEvery {
            dataStoreManager.putPreference(PreferenceDataStoreConstants.USER_KEY, "1")
        } returns Unit

        every {
            userMock.uid
        } returns "1"

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.name shouldBe ""
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not signup when email field is empty`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = "name"
        val email = ""
        val password = "password"
        val phoneNumber = "112345698"
        val confirmPassword = "password"

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        coEvery {
            dataStoreManager.putPreference(PreferenceDataStoreConstants.USER_KEY, "1")
        } returns Unit

        every {
            userMock.uid
        } returns "1"

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not signup when phoneNumber field is empty`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = "name"
        val email = "email"
        val password = "password"
        val phoneNumber = ""
        val confirmPassword = "password"

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not signup when password field is empty`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = "name"
        val email = "email"
        val password = ""
        val phoneNumber = "112345698"
        val confirmPassword = "password"

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not signup when confirmPassword field is empty`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = "name"
        val email = "email"
        val password = "password"
        val phoneNumber = "112345698"
        val confirmPassword = ""

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not signup when password and confirmPassword do not match`() = runTest {
        val userMock: FirebaseUser = mockk()

        val name = "name"
        val email = "email"
        val phoneNumber = "112345698"
        val password = "password"
        val confirmPassword = "confirmPassword"

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Success(userMock)

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.passwordMatchError shouldBe true
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should not login when return error`() = runTest {
        val name = "name"
        val email = "email"
        val phoneNumber = "112345698"
        val password = "password"
        val confirmPassword = "password"

        coEvery {
            repository.signup(name, email, password)
        } returns FirebaseResult.Error(FirebaseAuthException("ERROR_INVALID_EMAIL", "Invalid Email"))

        vm.updateInfo(name = name)
        vm.updateInfo(email = email)
        vm.updateInfo(password = password)
        vm.updateInfo(phoneNumber = phoneNumber)
        vm.updateInfo(confirmPassword = confirmPassword)
        vm.signup()

        val state = vm.state.value

        state.isSuccessRegister shouldBe false
        state.registerError shouldBe true
        state.passwordMatchError shouldBe false
        state.name shouldBe name
        state.email shouldBe email
        state.phoneNumber shouldBe phoneNumber
        state.isLoading shouldBe false
    }

    @Test
    fun `should hide dialog`() = runTest {
        vm.hideErrorDialog()

        val state = vm.state.value

        state.registerError shouldBe false
    }
}