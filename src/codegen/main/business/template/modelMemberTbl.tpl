#if ($header)
/*******************************************************************************
 * PROPRIETARY/CONFIDENTIAL
 * Copyright (c) 2004-2004 Gear Technologies Limited
 *
 * All rights reserved. This medium contains confidential and proprietary
 * source code and other information which is the exclusive property of
 * Gear Technologies Limited.  None of these materials may be used,
 * disclosed, transcribed, stored in a retrieval system, translated into
 * any other language or other computer language, or transmitted in any form
 * or by any means (electronic, mechanical, photocopied, recorded or
 * otherwise) without the prior written permission of Gear Technologies Limited.
 *******************************************************************************
 *
 * $Header: $
 * $Id: $
 * $Author: $
 * $Date: $ 
 * $Revision: $ 
 *
 *******************************************************************************
 */
#end
CREATE TABLE ${tableName}_member (
   $pk      id NOT NULL,
   Involvement      type3 NOT NULL,
   ObjId            id NOT NULL,
   ObjType          type NOT NULL
)
go

ALTER TABLE ${tableName}_member ADD 
    CONSTRAINT PK_${tableName}_member PRIMARY KEY CLUSTERED ($pk, Involvement, ObjType, ObjId)
go

ALTER TABLE ${tableName}_member ADD 
    CONSTRAINT IF1_${tableName}_member 
    FOREIGN KEY ($pk)
    REFERENCES ${tableName}($pk) 
    ON DELETE CASCADE
    ON UPDATE NO ACTION
go

ALTER TABLE ${tableName}_member ADD
    CONSTRAINT CK1_${tableName}_member  
    CHECK (Involvement in (${entity.sysMemberInvolvementInSql})) 
go   

ALTER TABLE ${tableName}_member ADD
    CONSTRAINT CK2_${tableName}_member
    CHECK (ObjType in ('Company', 'Party', 'User', 'BusinessObject'));
go   

CREATE NONCLUSTERED INDEX IE1_${tableName}_member ON ${tableName}_member (Involvement, objType, objId);

go
