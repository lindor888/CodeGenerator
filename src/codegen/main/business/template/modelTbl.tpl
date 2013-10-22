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
CREATE TABLE $tableName (
#foreach($field in $fields)
	$field
#end
);
#if($pk)
ALTER TABLE $tableName ADD 
    CONSTRAINT PK_$tableName PRIMARY KEY CLUSTERED ($pk);
go
#end
#foreach($field in ${entity.fieldListSysObjCurrency})
ALTER TABLE ${entity.tableName} ADD 
    CONSTRAINT IF${entity.codeGenerator.currentFK}_${entity.tableName}
    FOREIGN KEY (CurrencyId)
    REFERENCES currency(CurrencyId) 
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
go
#end
