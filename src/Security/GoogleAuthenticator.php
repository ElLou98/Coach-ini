<?php
namespace App\Security;

use App\Entity\User;
use App\Entity\Compte;// your user entity
use Doctrine\ORM\EntityManagerInterface;
use KnpU\OAuth2ClientBundle\Security\Authenticator\SocialAuthenticator;
use KnpU\OAuth2ClientBundle\Client\ClientRegistry;
use League\OAuth2\Client\Provider\GoogleUser;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\RedirectResponse;
use Symfony\Component\Routing\RouterInterface;
use Symfony\Component\Security\Core\User\UserProviderInterface;


class GoogleAuthenticator extends SocialAuthenticator
{
private $clientRegistry;
private $em;
private $router;

public function __construct(ClientRegistry $clientRegistry, EntityManagerInterface $em, RouterInterface $router)
{
$this->clientRegistry = $clientRegistry;
$this->em = $em;
$this->router = $router;
}

public function supports(Request $request)
{
return $request->getPathInfo() == '/connect/google/check' && $request->isMethod('GET');
}

public function getCredentials(Request $request)
{
    return $this->fetchAccessToken($this->getGoogleClient());
}



public function getUser($credentials, UserProviderInterface $userProvider)
{
/** @var GoogleUser $googleUser */
$googleUser = $this->getgoogleClient()
->fetchUserFromToken($credentials);

$username = $googleUser->getName();
$user = $this->em->getRepository('App:User')
    ->findOneBy(['username' => $username]);
$compte =$this->em->getRepository('App:Compte')
    ->findOneBy(['username' => $username]);
if (!$user ) {

    $user = new User();

    $user->setUsername($username);


    $this->em->persist($user);
    $this->em->flush();
}
if(!$compte){
    $compte =new compte();
    $compte->setUsername($googleUser->getName());
    $compte->setAdresseMail($googleUser->getEmail());
    $compte->setNom($googleUser->getLastName());
    $compte->setprenom($googleUser->getFirstName());
    $compte->setType('client');
    $this->em->persist($compte);
    $this->em->flush();


}

return $user ;

}

/**
* @return \KnpU\OAuth2ClientBundle\Client\OAuth2Client
*/
private function getGoogleClient()
{
return $this->clientRegistry
    ->getClient('google');
}



/**
* Called when authentication is needed, but it's not sent.
* This redirects to the 'login'.
 * @param Request $request The request that resulted in an AuthenticationException
 * @param \Symfony\Component\Security\Core\Exception\AuthenticationException $authException the exception that started
 * @return \Symfony\Component\HttpFoundation\Response
 */
public function start(Request $request, \Symfony\Component\Security\Core\Exception\AuthenticationException $authException)
{
return new RedirectResponse('/login');
}

/**

     * @param Request $request
     * @param \Symfony\Component\Security\Core\Exception\AuthenticationException $exception
     * @return \Symfony\Component\HttpFoundation\Response|null
 */

public function onAuthenticationFailure(Request $request,\Symfony\Component\Security\Core\Exception\AuthenticationException $exception)
    {

    }
/**

     * @param Request $request
     * @param \Symfony\Component\Security\Core\Authentication\Token\TokenInterface $token
    * @param string $providerKey The provider (i.e firewall) key
     * @return void
     */

public function onAuthenticationSuccess(Request $request,  \Symfony\Component\Security\Core\Authentication\Token\TokenInterface $token, $providerKey)
    {

    }

}