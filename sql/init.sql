DROP TABLE IF EXISTS doodlee_order_item;

DROP TABLE IF EXISTS doodlee_order;

CREATE TABLE doodlee_order (
  orderid         INTEGER NOT NULL UNIQUE,
  buyername       VARCHAR(50),
  buyeremail      VARCHAR(50),
  orderdate       DATE,
  ordertotalvalue NUMERIC(12, 2),
  address         TEXT,
  postcode        INTEGER,
  CONSTRAINT doodlee_order_pk PRIMARY KEY (orderid)
);

CREATE TABLE doodlee_order_item (
  orderitemid    INTEGER NOT NULL UNIQUE,
  orderid        INTEGER REFERENCES doodlee_order (orderid),
  saleprice      NUMERIC(12, 2),
  shippingprice  NUMERIC(12, 2),
  totalitemprice NUMERIC(12, 2),
  sku            TEXT,
  status         TEXT,
  CONSTRAINT doodlee_order_item_pk PRIMARY KEY (orderitemid)
);