var testCases = parseInt(prompt("Number of test cases"));
var exp = 1000000007;

function combination(games, slice)
{
  var combinations = [];

  for(var i = 0 ; i < games.length ; i++)
  {
    if(slice == 1)
    {
      combinations.push([games[i]]);
    }
    else
    {
      combination(games.slice(i+1), slice-1).forEach(function(val)
      {
        combinations.push([].concat(games[i], val));
      });
    }
  }
  return combinations;
}

for(var i = 0; i < testCases; i++)
{
	var size = parseInt(prompt("Size of game"))+1;
	var game = prompt("Enter game numbers");
  game = game.split(" ");
  for(var j = 0; j < game.length; j++)
  {
  	parseInt(game[j]);
  }
	var position = [];
  for(var j = size-1; j > 0; j--)
  {
    position.push(Math.pow(2,j));
  }
  var position2 = position;
  for(var j = 0; j < size-2; j++)
  {
  	position.push(position2.slice(1,size-1-j));
  }
  var combinations = combination(game, 2);
  sum = 0;
  for(var j = 0; j < combinations.length; j++)
  {
  	sum += combinations[j][0]*combinations[j][1]*position[j]
  }
	console.log(sum % exp);
}
