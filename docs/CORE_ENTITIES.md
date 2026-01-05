# Core Business Entities (Draft)

## Company

Represents one business using the CRM.

Key Responsibilities:

- Owns all data (users, leads, orders)
- Used for multi-tenant separation

---

## User

Represents a person using the CRM.

Key Responsibilities:

- Login to system
- Perform actions based on role

---

## Email

Represents an email reference inside CRM.

Key Responsibilities:

- Store email metadata only
- Act as source for leads/enquiries

---

## Lead / Enquiry

Represents a potential business opportunity.

Key Responsibilities:

- Track enquiry stages
- Convert into order

---

## Order

Represents a confirmed customer order.

Key Responsibilities:

- Track order status
- Link to invoices and delivery

---

## Purchase Order

Represents an order placed to supplier.

Key Responsibilities:

- Track procurement for customer orders

---

## Invoice

Represents proforma or sales invoice.

Key Responsibilities:

- Generate PDF
- Track payment status

---

## Payment

Represents payment received from customer.

Key Responsibilities:

- Close orders after full payment

---

## File / Attachment

Represents documents linked to any entity.

Key Responsibilities:

- Store file path and reference

---

## Entity Flow (High-Level)

Email → Lead → Order → Invoice → Payment → Closed
Order → Purchase Order → Supplier
