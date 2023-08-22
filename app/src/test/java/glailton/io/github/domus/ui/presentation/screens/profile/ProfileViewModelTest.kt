package glailton.io.github.domus.ui.presentation.screens.profile

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.models.User
import glailton.io.github.domus.firebase.FirebaseAuthRepositoryImpl
import glailton.io.github.domus.firebase.FirestoreRepositoryImpl
import glailton.io.github.domus.utils.CoroutineRules
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRules()

    private val firestoreRepository: FirestoreRepositoryImpl = mockk()
    private val repository: FirebaseAuthRepositoryImpl = mockk()
    private lateinit var vm: ProfileViewModel
    private val firebaseUserMock: FirebaseUser = mockk()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every {
            repository.currentUser
        } returns firebaseUserMock

        every {
            repository.currentUser?.uid
        } returns "1"

        vm = ProfileViewModel(repository, firestoreRepository)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should get user successfully`() = runTest {
        val userMock = User("User", "email@email", "26/10/1986", "photo", "85859985", "1")

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.name shouldBe userMock.name
        state.email shouldBe userMock.email
        state.photoUrl shouldBe userMock.photo
        state.phoneNumber shouldBe userMock.phoneNumber
        state.birthday shouldBe userMock.birthday
        state.isLoading shouldBe false
    }

    @Test
    fun `should not get user when return error`() = runTest {
        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Error(
            FirebaseAuthException(
                "ERROR_INVALID_EMAIL",
                "Invalid Email"
            )
        )

        vm.getUser()
        val state = vm.state.value

        state.user shouldBe null
        state.isLoading shouldBe false
    }

    @Test
    fun `should update user name info`() = runTest {
        val userMock = User("User", "email@email", "26/10/1986", "photo", "85859985", "1")

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.name shouldBe userMock.name
        state.email shouldBe userMock.email
        state.photoUrl shouldBe userMock.photo
        state.phoneNumber shouldBe userMock.phoneNumber
        state.birthday shouldBe userMock.birthday

        vm.updateInfo(name = "Test")

        val newState = vm.state.value

        newState.name shouldBe "Test"
    }

    @Test
    fun `should update user email info`() = runTest {
        val userMock = User("User", "email@email", "26/10/1986", "photo", "85859985", "1")

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.name shouldBe userMock.name
        state.email shouldBe userMock.email
        state.photoUrl shouldBe userMock.photo
        state.phoneNumber shouldBe userMock.phoneNumber
        state.birthday shouldBe userMock.birthday

        vm.updateInfo(email = "admin@email.com")

        val newState = vm.state.value

        newState.email shouldBe "admin@email.com"
    }

    @Test
    fun `should update user photoUrl info`() = runTest {
        val userMock = User("User", "email@email", "26/10/1986", "photo", "85859985", "1")

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.name shouldBe userMock.name
        state.email shouldBe userMock.email
        state.photoUrl shouldBe userMock.photo
        state.phoneNumber shouldBe userMock.phoneNumber
        state.birthday shouldBe userMock.birthday

        vm.updateInfo(photoUrl = "photo2")

        val newState = vm.state.value

        newState.photoUrl shouldBe "photo2"
    }

    @Test
    fun `should update user phoneNumber info`() = runTest {
        val userMock = User("User", "email@email", "26/10/1986", "photo", "85859985", "1")

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.name shouldBe userMock.name
        state.email shouldBe userMock.email
        state.photoUrl shouldBe userMock.photo
        state.phoneNumber shouldBe userMock.phoneNumber
        state.birthday shouldBe userMock.birthday

        vm.updateInfo(phoneNumber = "85996564")

        val newState = vm.state.value

        newState.phoneNumber shouldBe "85996564"
    }

    @Test
    fun `should update user birthday info`() = runTest {
        val userMock = User("User", "email@email", "26/10/1986", "photo", "85859985", "1")

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.name shouldBe userMock.name
        state.email shouldBe userMock.email
        state.photoUrl shouldBe userMock.photo
        state.phoneNumber shouldBe userMock.phoneNumber
        state.birthday shouldBe userMock.birthday

        vm.updateInfo(birthday = "30/05/1992")

        val newState = vm.state.value

        newState.birthday shouldBe "30/05/1992"
    }

    @Test
    fun `should save image successfully`() = runTest {
        val uriMock: Uri = mockk()

        mockkConstructor(Uri::class)

        coEvery {
            firestoreRepository.saveImage(uriMock,"1")
        } returns FirebaseResult.Success("uri")

        vm.saveImage(uriMock)

        val state = vm.state.value

        state.photoUrl shouldBe "uri"
        state.isLoading shouldBe false
    }

    @Test
    fun `should not save image when return error`() = runTest {
        val uriMock: Uri = mockk()
        mockkConstructor(Uri::class)

        coEvery {
            firestoreRepository.saveImage(uriMock,"1")
        } returns FirebaseResult.Error(
            FirebaseAuthException(
                "ERROR_INVALID_EMAIL",
                "Invalid Email"
            )
        )

        vm.saveImage(uriMock)
        val state = vm.state.value

        state.photoUrl shouldNotBe "uri"
        state.isLoading shouldBe false
    }

    @Test
    fun `should update user successfully`() = runTest {
        val userMock = User("User", "email@email", "26/10/1986", "photo", "85859985", "1")
        mockkConstructor(User::class)

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.isLoading shouldBe false
        state.name shouldBe userMock.name

        coEvery {
            firestoreRepository.updateUser(any())
        } returns FirebaseResult.Success(true)

        vm.updateInfo(name = "Test")
        vm.updateUser()

        val newState = vm.state.value

        newState.isLoading shouldBe false
        newState.isSaved shouldBe true
    }

    @Test
    fun `should not update user when return error`() = runTest {
        mockkConstructor(User::class)

        coEvery {
            firestoreRepository.updateUser(any())
        } returns FirebaseResult.Error(
            FirebaseAuthException(
                "ERROR_INVALID_EMAIL",
                "Invalid Email"
            )
        )

        vm.updateUser()
        val state = vm.state.value

        state.isSaved shouldBe false
        state.isLoading shouldBe false
    }
}