package com.example.furlogix.viewModels

import androidx.lifecycle.liveData
import com.furlogix.Database.Entities.User
import com.furlogix.email.EmailValidator
import com.furlogix.logger.ILogger
import com.furlogix.repositories.IUserRepository
import com.furlogix.viewmodels.UserViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class UserViewModelTests {

    private lateinit var userRepository: IUserRepository
    private lateinit var viewModel: UserViewModel
    private lateinit var logger : ILogger

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        userRepository = mockk()
        every { userRepository.getCurrentUserName() } returns liveData { "Alice" }
        every { userRepository.getCurrentUserEmail() } returns liveData {"alice@example.com"}
        every { userRepository.getCurrentUserIdAsFlow() } returns flowOf(42L)

        logger = mockk()
        every { logger.log(any(), any()) } just Runs

        viewModel = UserViewModel(logger, userRepository)
    }

    @After
    fun tearDown() {
        unmockkObject(userRepository)
        unmockkObject(EmailValidator)
    }

    @Test
    fun userViewModel_emptyName_CorrectErrorState(){
        viewModel.onNameChange("")
        Assert.assertEquals(false,viewModel.isFormValid())
        Assert.assertNotEquals("", viewModel.nameError)
        Assert.assertNotNull(viewModel.nameError)
    }

    @Test
    fun userViewModel_emptySurName_CorrectErrorState(){
        viewModel.onSurnameChange("")
        Assert.assertEquals(false,viewModel.isFormValid())
        Assert.assertNotEquals("", viewModel.surnameError)
        Assert.assertNotNull(viewModel.surnameError)
    }

    @Test
    fun userViewModel_emptyEmail_CorrectErrorState(){
        viewModel.onEmailChange("")
        mockkObject(EmailValidator.Companion)
        every { EmailValidator.ValidateEmail("") } returns false
        Assert.assertEquals(false,viewModel.isFormValid())
        Assert.assertNotEquals("", viewModel.emailError)
        Assert.assertNotNull(viewModel.emailError)
    }

    @Test
    fun userViewModel_invalidEmail_CorrectErrorState(){
        viewModel.onEmailChange("abcd123")
        mockkObject(EmailValidator.Companion)
        every { EmailValidator.ValidateEmail("abcd123") } returns false
        Assert.assertEquals(false,viewModel.isFormValid())
        Assert.assertNotEquals("", viewModel.emailError)
        Assert.assertNotNull(viewModel.emailError)
    }

    @Test
    fun addUser_ValidUser_Success(){
        val user = User(name = "tester",
            surname = "tester",
            email = "email123@gmail.com")
        mockkObject(EmailValidator.Companion)
        every { EmailValidator.ValidateEmail("email123@gmail.com") } returns true
        coEvery { userRepository.insert(user) } returns 1
        val latch = CountDownLatch(1)
        var extractedUserId = -1

        viewModel.addUser(user) { userId ->
            extractedUserId = userId.toInt()
            latch.countDown()
        }

        assertTrue("Callback not called in time", latch.await(1, TimeUnit.SECONDS))
        assertEquals(1, extractedUserId)
    }

    @Test
    fun addUser_ValidUser_Error(){
        val user = User(name = "",
            surname = "tester",
            email = "email123@gmail.com")
        mockkObject(EmailValidator.Companion)
        every { EmailValidator.ValidateEmail("email123@gmail.com") } returns true
        var extractedUserId = -1
        viewModel.addUser(user){ userId -> extractedUserId = userId.toInt() }
        Assert.assertEquals(-1, extractedUserId)
    }

    @Test
    fun populateCurrentUser_UpdatesViewModelProperty() = runTest{
        val mockUser = User(1, "tester", "tester", "test@test.com")
        coEvery { userRepository.getUserById(1) } returns mockUser
        viewModel.populateCurrentUser()
        advanceUntilIdle()
        assertEquals(mockUser, viewModel.currentUser.value)
    }
}