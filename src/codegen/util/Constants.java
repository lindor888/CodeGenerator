package codegen.util;

/**
 * 
 * @author zhajingzha
 *
 */
public interface Constants {

   String SPLITTER_COL_EN_CN = ",\uff0c";
   
   String PARAMETOR_CHARECTER = "?";
   
   String URL_SEPARATOR = "/";

   String TREE_NODE_SEPARATOR = "|";

   String SPLITTER_PLUS = "+";

   String SPLITTER_DASH = "-";

   String SPLITTER_DASH2 = " - ";

   String SPLITTER_DASH_CN = "\uff0d";

   String SPLITTER_WAVELINE = " ~ ";

   String SPLITTER_BLANK = " ";

   String SPLITTER_BLANK2 = "  ";

   String SPLITTER_BLANK3 = "   ";

   String SPLITTER_BLANK4 = "    ";

   String SPLITTER_ROW = ";";

   String SPLITTER_ROW_SPACE = "; ";

   String SPLITTER_COL = ", ";

   String SPLITTER_COMMA = ",";

   String SPLITTER_AND = "&";

   String SPLITTER_COMMA2 = ", ";

   String SPLITTER_FIELD_VALUE = ":";

   String SPLITTER_FIELD_VALUE2 = ": ";

   String SPLITTER_PLURAL = ".s";

   String SPLITTER_DOT = ".";

   String SPLITTER_NAME = "S,S";

   String PERCENT = "%";

   String LEFT_PARENTTHESES = "(";

   String RIGHT_PARENTTHESES = ")";

   String LEFT = "left";

   String RIGHT = "right";

   String TOP = "top";

   String BOTTOM = "bottom";

   String JS_SEPARATOR = "\\";

   String TRUE = "1";

   String FALSE = "0";

   String DAY = "Day";

   String MONTH = "Month";

   String QUARTER = "Quarter";

   String YEAR = "Year";

   String UNDERLINE = "_";
   
   String SPLIT_PROP_AREA = "->";
   
   String LANGUAGE_EN = "en";
   
   int NUMBER_MAX_FILE_NAME_SIZE = 64;

   int NUMBER_MAX_SIZE = 8;

   int ROLE_EVERYONE = -1;

   int ORDER_STEP = 50;

   int ORDER_START = 100;

   int KB = 1024;

   int MILLION_BYTE = 1024 * 1024;

   int APPROVAL_NAME_MAX_LEN = 64;

   int APPROVAL_DEC_MAX_LEN = 2000;

   int DAY_COUNT_OF_WEEK = 7;

   int HOUR_COUNT_OF_DAY = 24;

   int MAXLEN_STRING1 = 64;

   int MAXLEN_STRING2 = 256;

   int MAXLEN_STRING3 = 2000;

   int MAXLEN_STRING4 = 1073741823;

   int MAXLEN_TYPE = 10;

   int FLOAT_POINT = 15;

   double MAX_FLOAT13 = 100000000000.0;
   
   double MAX_FLOAT15 = 10000000000000.0;

   double MAX_FLOAT16 = 100000000000000.0;

   double MAX_FLOAT18 = 10000000000000000.0;
   
   String OBJ_TYPE_USER = "User";

   String OBJ_TYPE_PARTY = "Party";
   
   String OBJ_TYPE_GROUP = "Group";
   
   String OBJ_TYPE_COMPANY = "Company";
   
   String OBJ_TYPE_USERPARTY = "User,Party";
   
   String OBJ_TYPE_USERPARTYGROUP = "User,Party,Group";
   
   String OBJ_TYPE_USERPARTYCOMPANY = "User,Party,Company";
   
   String OBJ_TYPE_PRODUCT = "Product";
   
   String OBJ_TYPE_OTHER = "BusinessObject";
   
   String FIELD_TYPE_STRING = "String";
   
   String FIELD_TYPE_INTEGER = "Integer";
   
   String FIELD_TYPE_BOOLEAN = "Boolean";
   
   String FIELD_TYPE_DATE = "Date";
   
   String FIELD_TYPE_SET = "Set";
   
   String FIELD_TYPE_DOBULE = "Double";
   
   String FIELD_COLUMN_TYPE_ID = "id";
   
   String FIELD_COLUMN_TYPE_SHORT_STR = "type3";
   
   String FIELD_COLUMN_TYPE_BIG_STR = "string2";
   
   String FIELD_COLUMN_TYPE_TS = "ts";
   
   String FIELD_COLUMN_TYPE_BOOLEAN = "boolean";
   
   String FIELD_COLUMN_TYPE_ORDER = "ord";
   
   String FIELD_COLUMN_TYPE_NUMBER = "decimal(20, 2)";
   
   
}