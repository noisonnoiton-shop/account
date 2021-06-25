--sequence
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;
select event_seq.nextval;

--account
INSERT INTO account
(id, username, name, password, mobile, address, scope, created_at)
VALUES
(1, 'a','a','asdfasdf11','01012345678','경기도 성남시 분당구','customer', NOW());

--account_events
INSERT INTO account_event
(id, domain, account_id, event_type, payload, tx_id, created_at)
VALUES
(1, 'account', 1, 'AccountCreated'
, '{"id": 1,"username": "a","name": "a","mobile": "01012345678","address": "경기도 성남시 분당구","scope": "customer"}'
, 'tx123123123'
, NOW());

INSERT INTO account_bank
(account_id,bank_name,bank_number,active,created_at)
VALUES
(1,'sc','123123123123','active',NOW());

INSERT INTO account_bank_event
(id,account_bank_id,domain,event_type,payload,tx_id,created_at)
VALUES
(1,1,'accountBank','AccountBankCreated'
,'{"id":1,"accountId":1,"bankName":"sc","bankNumber":"123123123123","active":"active"}'
,'tx123123127'
,NOW());