grammar RestFilterDSL;

query :                                                     #QueryRoot
      | logical_expression                                  #LogicalExpression
      | EOF                                                 #EndOfFile
      ;

logical_expression : logical_expression AND logical_expression             #AndExpression
    | logical_expression OR logical_expression              #OrExpression
    | LPAREN logical_expression RPAREN                      #ParenthesizedExpression
    | fieldName comparison_operator fieldValue              #ComparisonExpression
//    | NOT logical_expression                                #NegationExpression
    ;

comparison_operator :                                       #ComparisonOperator
    | EQ                                                    #EQUALS_TO
    | NE                                                    #NOT_EQUALS_TO
    | GT                                                    #GREATER_THAN
    | GE                                                    #GREATER_THAN_OR_EQUALS_TO
    | LT                                                    #LESS_THAN
    | LE                                                    #LESS_THAN_OR_EQUALS_TO
    ;

fieldName:
    | FIELD_NODE('.' FIELD_NODE)*
    ;

fieldValue :
    | STRING
    | NUMBER
    | DECIMAL
    ;

AND : 'and' | ',' ;
OR  : 'or' ;

TRUE  : 'true' ;
FALSE : 'false' ;

PLUS  : '+' ;
MINUS : '-' ;


GT : '>' ;
GE : '>=' ;
LT : '<' ;
LE : '<=' ;
EQ : '==' ;
NE : '!=' ;

NOT: '!' ;

LPAREN : '(' ;
RPAREN : ')' ;

DECIMAL : '-'?[0-9]+('.'[0-9]+)? ;
NUMBER : [0-9]+ ;

//FIELD : [a-zA-Z_][a-zA-Z_0-9]*;
FIELD_NODE: [a-zA-Z_][a-zA-Z_0-9]*;

WS : [ \r\t\u000C\n]+ -> skip ;


fragment SQSTR : '\'' (~['"] | DQSTR)* '\'';
fragment DQSTR : '"'  (~['"] | SQSTR)* '"';

STRING :  SQSTR | DQSTR ;
