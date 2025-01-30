using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using TWservice2.Models;

namespace TWservice2.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        [HttpGet]
        public List<User> GetUsers()
        {
            List<User> users = new List<User>();
            using (var db = new p10_tiffinwalaContext())
            {
                users = db.Users.ToList();
            }
            return users;
        }


    }
}
