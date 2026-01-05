# Status and Stage Definitions

## Lead / Enquiry Stages

Represents progress of a customer enquiry.

Allowed Stages:

1. NEW – Enquiry received
2. ACKNOWLEDGED – Client informed that enquiry is received
3. QUOTATION_SENT – Offer or quotation shared
4. FOLLOW_UP – Waiting for client response
5. NEGOTIATION – Price / terms discussion
6. CONVERTED – Client confirmed order
7. LOST – Enquiry closed without order

Rules:

- Only CONVERTED leads can create Orders
- LOST leads cannot move forward

---

## Order Status

Represents lifecycle of a confirmed order.

Allowed Status:

1. CREATED – Order created from lead
2. ACTIVE – Order under processing
3. READY_FOR_DISPATCH – Goods ready
4. DISPATCHED – Sent to customer
5. CLOSED – Delivered and fully paid
6. CANCELLED – Order cancelled

Rules:

- CLOSED requires full payment
- DISPATCHED requires invoice

---

## Purchase Order Status

Allowed Status:

1. CREATED
2. SENT_TO_SUPPLIER
3. CONFIRMED
4. RECEIVED
5. CLOSED

---

## Invoice Type

- PROFORMA
- SALES

---

## Invoice Status

- GENERATED
- SENT
- PAID
- CANCELLED

---

## Payment Status

- PENDING
- PARTIAL
- FULL

---

## Forbidden Transitions (Initial Rules)

- LOST lead cannot be converted to Order
- CLOSED order cannot be edited
- PAID invoice cannot be cancelled
