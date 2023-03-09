package org.acme.entity;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class InvoiceTest
{
	@Test
	@TestTransaction
	void testContraints_notNull()
	{
		final Invoice bean = new Invoice(null);
		assertThrowsExactly(ConstraintViolationException.class, bean::persistAndFlush);
	}

	@Test
	@TestTransaction
	void testContraints_regex()
	{
		final Invoice bean = new Invoice("invalid-invoiceNo");
		assertThrowsExactly(ConstraintViolationException.class, bean::persistAndFlush);
	}
}
