package com.plcoding.testingcourse.part6

import com.google.firebase.auth.FirebaseAuth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderServiceTest{

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var emailClient: EmailClient
    private lateinit var orderService: OrderService

    @BeforeEach
    fun setUp(){
        firebaseAuth = mockk(relaxed = true)
        emailClient = mockk(relaxed = true)

        orderService = OrderService(
            auth = firebaseAuth,
            emailClient = emailClient
        )
    }

    @Test
    fun `Test sending right email and customer for sending email`(){
        val customerEmail = "jordan@test.com"
        val productName = "Ice cream"

        every { firebaseAuth.currentUser?.isAnonymous } returns false

        orderService.placeOrder(customerEmail, productName)

        verify {
            emailClient.send(
                Email(
                    subject = "Order Confirmation",
                    content = "Thank you for your order of $productName.",
                    recipient = customerEmail
                )
            )

            emailClient.send(match {
                it.subject == "Order Confirmation" &&
                        it.content == "Thank you for your order of $productName." &&
                        it.recipient == customerEmail
            })
        }
    }
}