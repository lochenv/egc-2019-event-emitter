<html xmlns="http://www.w3.org/1999/html">
<body>
<p>
    Dear ${firstName} ${lastName},
</p>
<p>
    Welcome to <span style="font-weight: bold">${tournamentName}</span> round <span
        style="font-weight: bold"> ${roundNumber}</span> !
</p>
<#if bye>
    <p>
        You are <span style="font-weight: bold">bye</span> for this round.
    </p>
<#else>
    <p>
        Your opponent is <span style="font-weight: bold">${opponentFirstName} ${opponentLastName} ${opponentLevel}</span>.
        Please go to table <span style="font-weight: bold">${table}</span>
        <br>
        You are playing <span style="font-weight: bold">${color}</span>
    </p>
</#if>

<p>
    Tournament rules:
    <br>
    This is a ${boardSize}x${boardSize} tournament.
    <br>
    ${description}
</p>
<p>
    Have a nice game and a lovely time in Brussels !
</p>
<p style="font-size: .75em">
    If you don't want to receive this e-mails please reply this address
    <a href="mailto:info@egc2019.eu">info@egc2019.eu</a>
</p>
</body>
</html>
