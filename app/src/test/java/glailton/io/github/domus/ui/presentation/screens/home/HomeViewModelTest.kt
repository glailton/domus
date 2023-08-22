package glailton.io.github.domus.ui.presentation.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import glailton.io.github.domus.core.data.FirebaseResult
import glailton.io.github.domus.core.models.User
import glailton.io.github.domus.firebase.FirebaseAuthRepositoryImpl
import glailton.io.github.domus.firebase.FirestoreRepositoryImpl
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
class HomeViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = CoroutineRules()

    private val firestoreRepository: FirestoreRepositoryImpl = mockk()
    private val repository: FirebaseAuthRepositoryImpl = mockk()
    private lateinit var vm: HomeViewModel
    private val firebaseUserMock: FirebaseUser = mockk()

    @Before
    fun setup() {
        every {
            repository.currentUser
        } returns firebaseUserMock

        every {
            repository.currentUser?.uid
        } returns "1"

        vm = HomeViewModel(repository, firestoreRepository)
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should get user successfully`() = runTest {
        val userMock: User = mockk()

        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Success(userMock)

        vm.getUser()

        val state = vm.state.value

        state.user shouldBe userMock
        state.isLoading shouldBe false
    }

    @Test
    fun `should not login when return error`() = runTest {
        coEvery {
            firestoreRepository.getUser("1")
        } returns FirebaseResult.Error(FirebaseAuthException("ERROR_INVALID_EMAIL", "Invalid Email"))

        val state = vm.state.value

        state.user shouldBe null
        state.isLoading shouldBe false
    }
}