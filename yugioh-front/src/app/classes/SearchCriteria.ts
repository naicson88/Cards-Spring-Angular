/*operations

    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_EQUAL,
    LESS_THAN_EQUAL,
    NOT_EQUAL,
    EQUAL,
    MATCH,
    MATCH_START,
    MATCH_END,
    IN,
    NOT_IN

*/ 

export class SearchCriteria {
    private key: string;
    private operation: string;
    private value: Object;
    private orPredicate: boolean;
    private values: Array<string>;

    public criterios(key: string, operation: string, value: Object){
        this.key = key;
        this.operation = operation;
        this.value = value;

    }
    
}