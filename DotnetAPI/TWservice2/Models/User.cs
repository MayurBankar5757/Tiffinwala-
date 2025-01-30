using System;
using System.Collections.Generic;

namespace TWservice2.Models
{
    public partial class User
    {
        public User()
        {
            CustomerOrderLogs = new HashSet<CustomerOrderLog>();
            CustomerSubscribedPlans = new HashSet<CustomerSubscribedPlan>();
            CustomerSubscriptionsJunctions = new HashSet<CustomerSubscriptionsJunction>();
            Feedbacks = new HashSet<Feedback>();
            Vendors = new HashSet<Vendor>();
        }

        public int Uid { get; set; }
        public string Fname { get; set; } = null!;
        public string Lname { get; set; } = null!;
        public string Email { get; set; } = null!;
        public int Rid { get; set; }
        public string? Area { get; set; }
        public string? City { get; set; }
        public string? Pincode { get; set; }
        public string? State { get; set; }
        public string Password { get; set; } = null!;
        public string Contact { get; set; } = null!;

        public virtual Role RidNavigation { get; set; } = null!;
        public virtual ICollection<CustomerOrderLog> CustomerOrderLogs { get; set; }
        public virtual ICollection<CustomerSubscribedPlan> CustomerSubscribedPlans { get; set; }
        public virtual ICollection<CustomerSubscriptionsJunction> CustomerSubscriptionsJunctions { get; set; }
        public virtual ICollection<Feedback> Feedbacks { get; set; }
        public virtual ICollection<Vendor> Vendors { get; set; }
    }
}
