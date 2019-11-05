   package crm.entities;

public enum TitleStat {
	Failer,Stranger,Disappointment,StarterPack,Rising,Trending;
}

/*
 
	mapToReturn.put("positiveList", positivelist);
		mapToReturn.put("negativeList", negativelist);
		mapToReturn.put("positiveListHalfNegative", positivelisthalfnegative);
		mapToReturn.put("positiveListHalfPositive", positivelisthalfpositive);
		mapToReturn.put("negativeListHalfNegative", negativelisthalfnegative);
		mapToReturn.put("negativeListHalfPositive", negativelisthalfpositive);
 *****************************STAT System*********************************************
FROM   StarterPack  TO   Rising  //code1   :  in positiveList 


     FROM   Rising  TO   Trending  //code2  :  in  positiveListHalfPositive


FROM   Trending  TO   Rising //code3 :  in positiveListHalfNegative


FROM   Rising  TO    Disappointment //code4 : in negativeList

FROM   Disappointment  TO  Rissing  //code9  in positiveList

FROM  StarterPack   TO   Disappointment //code5 :  in negativeList


FROM  Disappointment   TO   Stranger //code6 : in negativeListHalfNegative



FROM   Stranger  TO   Disappointment  //code8  in negativeListHalfPositive



 FROM    Stranger TO   Failer //code7 : if one week it's stays in negativeListHalfNegative test every day
 
 
 */